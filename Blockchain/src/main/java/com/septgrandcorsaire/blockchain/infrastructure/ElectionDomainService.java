package com.septgrandcorsaire.blockchain.infrastructure;

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

    public BlockChain createBlockchainForElection(final ElectionQuery query) {
        verifyRequestValidity(query.getElectionName());
        final BlockChain blockChain = new BlockChain(MINING_DIFFICULTY);
        blockChain.addBlock(blockChain.newBlock(query.toJson()));
        BlockchainDAO.INSTANCE.addBlockchain(query.getElectionName(), blockChain);
        return blockChain;
    }

    private void verifyRequestValidity(final String electionName) {
        if (BlockchainDAO.INSTANCE.electionAlreadyExistsWithThisName(electionName)) {
            throw new IllegalArgumentException("the election '" + electionName + "' already exists");
        }
    }
}
