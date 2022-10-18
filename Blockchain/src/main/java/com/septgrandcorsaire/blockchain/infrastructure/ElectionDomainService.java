package com.septgrandcorsaire.blockchain.infrastructure;

import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class ElectionDomainService {

    private static final int MINING_DIFFICULTY = 4;

    public BlockChain createBlockchainForElection(final ElectionQuery query) {
        verifyRequestValidity(query);
        final BlockChain blockChain = new BlockChain(MINING_DIFFICULTY);
        blockChain.addBlock(blockChain.newBlock(query.toJson()));
        //todo add the blockChain to a map of Blockchains in a DAO (infrastructure class)
        return blockChain;
    }

    private void verifyRequestValidity(final ElectionQuery query) {
        if (false) { //todo with a list of election names (map keys)
            throw new IllegalArgumentException("the election '" + query.getElectionName() + "' already exists");
        }
    }
}
