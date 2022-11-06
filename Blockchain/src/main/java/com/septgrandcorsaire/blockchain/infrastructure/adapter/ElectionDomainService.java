package com.septgrandcorsaire.blockchain.infrastructure.adapter;

import com.septgrandcorsaire.blockchain.api.error.exception.*;
import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.application.VoteQuery;
import com.septgrandcorsaire.blockchain.domain.*;
import com.septgrandcorsaire.blockchain.infrastructure.dao.BlockchainRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class ElectionDomainService {

    @Value("${mining.difficulty}")
    private static int MINING_DIFFICULTY;

    public BlockChain getBlockchainForElection(final String electionName) {
        BlockChain blockChain = BlockchainRepository.INSTANCE.getBlockchain(electionName);
        verifyExistingElection(blockChain, electionName);
        handleFinishedElection(blockChain);
        return blockChain;
    }

    private void handleFinishedElection(BlockChain blockChain) {
        ElectionInitializationData electionInitializationData = blockChain.getInitializationData();
        if (LocalDateTime.now().isAfter(electionInitializationData.getClosingDate())) {
            ElectionAlreadyFinishedException exception = new ElectionAlreadyFinishedException(String.format(ErrorCode.ELECTION_ALREADY_FINISHED.getDefaultMessage(), electionInitializationData.getElectionName(), electionInitializationData.getClosingDate()));
            exception.setElectionResult(ElectionResult.builder()
                    .startingDate(electionInitializationData.getStartingDate())
                    .closingDate(electionInitializationData.getClosingDate())
                    .candidatesResults(blockChain.getVotingBlock().stream()
                            .map(Block::getData)
                            .map(data -> ((VotingData) data))
                            .map(VotingData::getCandidateName)
                            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                    .build()
            );
            throw exception;
        }
    }

    private void verifyExistingElection(BlockChain blockChain, String query) {
        if (blockChain == null) {
            throw new ElectionNotFoundException(String.format(ErrorCode.NOT_FOUND_ELECTION.getDefaultMessage(), query));
        }
    }

    public BlockChain createBlockchainForElection(final ElectionQuery query) {
        verifyCreateElectionRequestValidity(query.getElectionName());
        final BlockChain blockChain = new BlockChain(query.getElectionName(), MINING_DIFFICULTY);
        blockChain.addBlock(blockChain.newBlock(ElectionInitializationData.fromElectionQuery(query), 0, null));
        BlockchainRepository.INSTANCE.addBlockchain(query.getElectionName(), blockChain);
        return blockChain;
    }

    private void verifyCreateElectionRequestValidity(final String electionName) {
        if (BlockchainRepository.INSTANCE.electionAlreadyExistsWithThisName(electionName)) {
            throw new IllegalArgumentException("the election '" + electionName + "' already exists");
        }
    }

    public Block voteInElection(VoteQuery query) {
        BlockChain blockChain = BlockchainRepository.INSTANCE.getBlockchain(query.getElectionName());
        verifyExistingElection(blockChain, query.getElectionName());
        verifyThatTheVoteIsTakenAfterTheElectionHasBegun(query, blockChain.getInitializationData());
        verifyThatTheVoteIsTakenBeforeTheElectionIsOver(query, blockChain.getInitializationData());
        query = verifyThatBlankVotesAreAllowed(query, blockChain.getInitializationData());
        verifyNamePartOfTheCandidates(blockChain.getInitializationData(), query);
        Block newVoteBlock = blockChain.newBlock(VotingData.fromVoteQuery(query));
        blockChain.addBlock(newVoteBlock);
        return newVoteBlock;
    }

    private VoteQuery verifyThatBlankVotesAreAllowed(VoteQuery query, ElectionInitializationData initializationData) {
        if (query.getCandidateName() == null || query.getCandidateName().isBlank()) {
            if (initializationData.getCandidates().contains("blank_votes")) {
                return query.setCandidateName("blank_votes");
            } else {
                throw new IllegalPayloadArgumentException(ErrorCode.REQUIRED_PARAMETER, String.format(ErrorCode.REQUIRED_PARAMETER.getDefaultMessage(), "candidate_name"));
            }
        }
        return query;
    }

    public void deleteAllElections() {
        BlockchainRepository.INSTANCE.clearRepository();
    }

    private void verifyThatTheVoteIsTakenAfterTheElectionHasBegun(VoteQuery query, ElectionInitializationData electionInitializationData) {
        if (query.getVotingDate().isBefore(electionInitializationData.getStartingDate())) {
            throw new ElectionNotStartedException(String.format(ErrorCode.ELECTION_NOT_STARTED.getDefaultMessage(), query.getElectionName(), electionInitializationData.getStartingDate()));
        }
    }

    private void verifyThatTheVoteIsTakenBeforeTheElectionIsOver(VoteQuery query, ElectionInitializationData electionInitializationData) {
        if (query.getVotingDate().isAfter(electionInitializationData.getClosingDate())) {
            throw new ElectionAlreadyFinishedException(String.format(ErrorCode.ELECTION_ALREADY_FINISHED.getDefaultMessage(), query.getElectionName(), electionInitializationData.getClosingDate()));
        }
    }


    private void verifyNamePartOfTheCandidates(ElectionInitializationData electionInitializationData, VoteQuery query) {
        if (!electionInitializationData.getCandidates().contains(query.getCandidateName())) {
            throw new IllegalArgumentException("the name '" + query.getCandidateName() + "' is not part of the " + query.getElectionName() + "'s candidates");
        }
    }
}
