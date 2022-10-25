package com.septgrandcorsaire.blockchain.infrastructure.dao;

import com.septgrandcorsaire.blockchain.domain.BlockChain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BlockchainDAOTest {

    static BlockChain blockChainOne, blockChainTwo;

    @BeforeAll
    static void initDAO() {
        blockChainOne = new BlockChain("blockchain_one", 4);
        BlockchainDAO.INSTANCE.addBlockchain("blockchain_one", blockChainOne);
        blockChainTwo = new BlockChain("blockchain_two", 4);
        BlockchainDAO.INSTANCE.addBlockchain("blockchain_two", blockChainTwo);
    }


    @Test
    void testGetBlockchain() {
        String query = "blockchain_one";
        BlockChain actual = BlockchainDAO.INSTANCE.getBlockchain(query);
        assertThat(actual).isEqualTo(blockChainOne);
    }

    @Test
    void testGetBlockchainNotExisting() {
        String query = "not_existing";
        BlockChain actual = BlockchainDAO.INSTANCE.getBlockchain(query);
        assertThat(actual).isEqualTo(null);
    }

    @Test
    void electionAlreadyExistsWithThisName() {
        String queryName = "blockchain_two";
        Boolean actual = BlockchainDAO.INSTANCE.electionAlreadyExistsWithThisName(queryName);
        assertThat(actual).isTrue();
    }
}