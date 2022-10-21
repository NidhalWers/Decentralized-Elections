package com.septgrandcorsaire.blockchain.infrastructure;

import com.septgrandcorsaire.blockchain.api.error.exception.ElectionNotFoundException;
import com.septgrandcorsaire.blockchain.api.error.exception.ErrorCode;
import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.application.VoteQuery;
import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.domain.ElectionInitializationData;
import com.septgrandcorsaire.blockchain.domain.VotingData;
import com.septgrandcorsaire.blockchain.infrastructure.dao.BlockchainDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class ElectionDomainService {

    @Value("${mining.difficulty}")
    private static int MINING_DIFFICULTY;

    public BlockChain getBlockchainForElection(final String electionName) {
        BlockChain blockChain = BlockchainDAO.INSTANCE.getBlockchain(electionName);
        verifyExistingElection(blockChain, electionName);
        return blockChain;
    }

    private static void verifyExistingElection(BlockChain blockChain, String query) {
        if (blockChain == null) {
            throw new ElectionNotFoundException(String.format(ErrorCode.NOT_FOUND_ELECTION.getDefaultMessage(), query));
        }
    }

    public BlockChain createBlockchainForElection(final ElectionQuery query) {
        verifyCreateElectionRequestValidity(query.getElectionName());
        final BlockChain blockChain = new BlockChain(query.getElectionName(), MINING_DIFFICULTY);
        blockChain.addBlock(blockChain.newBlock(ElectionInitializationData.fromElectionQuery(query), 0, null));
        BlockchainDAO.INSTANCE.addBlockchain(query.getElectionName(), blockChain);
        return blockChain;
    }

    private void verifyCreateElectionRequestValidity(final String electionName) {
        if (BlockchainDAO.INSTANCE.electionAlreadyExistsWithThisName(electionName)) {
            throw new IllegalArgumentException("the election '" + electionName + "' already exists");
        }
    }

    public Block voteInElection(VoteQuery query) {
        BlockChain blockChain = BlockchainDAO.INSTANCE.getBlockchain(query.getElectionName());
        verifyExistingElection(blockChain, query.getElectionName());
        //if (blockChain.getInitializationData().getData()) //todo candidate name
        Block newVoteBlock = blockChain.newBlock(VotingData.fromVoteQuery(query));
        blockChain.addBlock(newVoteBlock);
        return newVoteBlock;
    }
}
