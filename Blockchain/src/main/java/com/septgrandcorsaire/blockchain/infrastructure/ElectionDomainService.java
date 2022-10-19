package com.septgrandcorsaire.blockchain.infrastructure;

import com.septgrandcorsaire.blockchain.api.error.exception.ElectionNotFoundException;
import com.septgrandcorsaire.blockchain.api.error.exception.ErrorCode;
import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
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
        if (blockChain == null) {
            throw new ElectionNotFoundException(String.format(ErrorCode.NOT_FOUND_ELECTION.getDefaultMessage(), electionName));
        }
        return blockChain;
    }

    public BlockChain createBlockchainForElection(final ElectionQuery query) {
        verifyCreateElectionRequestValidity(query.getElectionName());
        final BlockChain blockChain = new BlockChain(query.getElectionName(), MINING_DIFFICULTY);
        blockChain.addBlock(blockChain.newBlock(query.toJson()));
        BlockchainDAO.INSTANCE.addBlockchain(query.getElectionName(), blockChain);
        return blockChain;
    }

    private void verifyCreateElectionRequestValidity(final String electionName) {
        if (BlockchainDAO.INSTANCE.electionAlreadyExistsWithThisName(electionName)) {
            throw new IllegalArgumentException("the election '" + electionName + "' already exists");
        }
    }
}
