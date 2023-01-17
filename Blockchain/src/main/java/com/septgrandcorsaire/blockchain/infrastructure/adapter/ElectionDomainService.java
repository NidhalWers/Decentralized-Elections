package com.septgrandcorsaire.blockchain.infrastructure.adapter;

import com.septgrandcorsaire.blockchain.api.error.exception.*;
import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.application.VoteQuery;
import com.septgrandcorsaire.blockchain.domain.*;
import com.septgrandcorsaire.blockchain.infrastructure.dao.ApiKeyRepository;
import com.septgrandcorsaire.blockchain.infrastructure.dao.BlockchainRepository;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.*;
import com.septgrandcorsaire.blockchain.infrastructure.service.ApiKeyGenerator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class ElectionDomainService {

    @Value("${mining.difficulty}")
    private int MINING_DIFFICULTY;

    public MessageElectionResult getBlockchainForElection(final String electionName, final String electionStatus) {
        BlockChain blockChain = BlockchainRepository.INSTANCE.getBlockchain(electionName, electionStatus);
        verifyExistingElection(blockChain, electionName);
        MessageElectionResult message = handleFinishedElection(blockChain, electionStatus);
        if (message != null) return message;
        return MessageOngoingElection.builder()
                .blockChain(blockChain)
                .status(electionStatus)
                .build(); //todo use of status;
    }

    public MessageElectionResult getBlockchainForElection(final String electionName) {
        return getBlockchainForElection(electionName, null);
    }

    private MessageFinishedElection handleFinishedElection(BlockChain blockChain, String electionStatus) {
        ElectionInitializationData electionInitializationData = blockChain.getInitializationData();
        if (LocalDateTime.now().isAfter(electionInitializationData.getClosingDate())) {
            //ElectionAlreadyFinishedException exception = new ElectionAlreadyFinishedException(String.format(ErrorCode.ELECTION_ALREADY_FINISHED.getDefaultMessage(), electionInitializationData.getElectionName(), electionInitializationData.getClosingDate()));
            MessageFinishedElection message = MessageFinishedElection.builder()
                    .electionResult(ElectionResult.builder()
                            .startingDate(electionInitializationData.getStartingDate())
                            .closingDate(electionInitializationData.getClosingDate())
                            .candidatesResults(blockChain.getVotingBlock().stream()
                                    .map(Block::getData)
                                    .map(data -> ((VotingData) data))
                                    .map(VotingData::getCandidateName)
                                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting())))
                            .build()
                    )
                    .status(electionStatus)
                    .build();
            //throw exception;
            return message;
        }
        return null;
    }

    private void verifyExistingElection(BlockChain blockChain, String query) {
        if (blockChain == null) {
            throw new ElectionNotFoundException(String.format(ErrorCode.NOT_FOUND_ELECTION.getDefaultMessage(), query));
        }
    }

    public MessageBlockchainCreated createBlockchainForElection(final ElectionQuery query) {
        verifyCreateElectionRequestValidity(query.getElectionName(), query.getElectionStatus());

        final BlockChain blockChain = new BlockChain(query.getElectionName(), MINING_DIFFICULTY);
        blockChain.addBlock(blockChain.newBlock(ElectionInitializationData.fromElectionQuery(query), 0, null));
        BlockchainRepository.INSTANCE.addBlockchain(query.getElectionName(), blockChain, query.getElectionStatus());

        final String apiKey = ApiKeyGenerator.generateKey();
        ApiKeyRepository.INSTANCE.addKey(blockChain.getName(), apiKey);

        return MessageBlockchainCreated.of(blockChain, apiKey, query.getElectionStatus());
    }

    private void verifyCreateElectionRequestValidity(final String electionName, String electionStatus) {
        if (BlockchainRepository.INSTANCE.electionAlreadyExistsWithThisNameAndStatus(electionName, electionStatus)) {
            throw new IllegalArgumentException("the election '" + electionName + "'" + (electionStatus != null ? ", with the status : " + electionStatus : "") + " already exists");
        }
    }

    public Block voteInElection(VoteQuery query) {
        BlockChain blockChain = BlockchainRepository.INSTANCE.getBlockchain(query.getElectionName(), query.getElectionStatus());
        verifyExistingElection(blockChain, query.getElectionName());
        verifyThatTheVoteIsTakenAfterTheElectionHasBegun(query, blockChain.getInitializationData());
        verifyThatTheVoteIsTakenBeforeTheElectionIsOver(query, blockChain.getInitializationData());
        query = verifyThatBlankVotesAreAllowed(query, blockChain.getInitializationData());
        verifyNamePartOfTheCandidates(blockChain.getInitializationData(), query);
        verifyVoterHasNotAlreadyVoted(blockChain.getVoterBlock(), query);
        //add voting block
        Block newVoteBlock = blockChain.newBlock(VotingData.fromVoteQuery(query));
        blockChain.addBlock(newVoteBlock);
        //add voter block
        Block newVoterBlock = blockChain.newBlock(VoterData.fromVoteQuery(query));
        blockChain.addBlock(newVoterBlock);
        return newVoteBlock;
    }

    public MessageVoteInElection getVoteInElection(String election, String status, String vote) {
        BlockChain blockChain = BlockchainRepository.INSTANCE.getBlockchain(election, status);
        verifyExistingElection(blockChain, election);
        if (vote == null || vote.isBlank()) {
            return MessageVoteInElection.of(blockChain);
        }
        var votingBlocks = blockChain.getVotingBlock();
        var result = votingBlocks.stream()
                .filter(block -> vote.equals(block.getHash()))
                .findFirst();
        return result.map(block -> MessageVoteInElection.of(block, blockChain)).orElseGet(() -> MessageVoteInElection.of(blockChain));
    }

    private void verifyVoterHasNotAlreadyVoted(List<Block> votingBlock, VoteQuery query) {
        List<String> voterWithThisId = votingBlock.stream()
                .map(block -> ((VoterData) block.getData()).getVoterId())
                .filter(voter -> voter.equals(query.getVoterId()))
                .collect(Collectors.toList());
        if (!voterWithThisId.isEmpty()) {
            throw new VoterHasAlreadyVotedException(String.format(ErrorCode.HAS_ALREADY_VOTED.getDefaultMessage(), query.getVoterId(), query.getElectionName()));
        }
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
        ApiKeyRepository.INSTANCE.clearRepository();
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
