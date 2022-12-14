package com.septgrandcorsaire.blockchain.infrastructure.adapter;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BlockchainDomainServiceTest {

    @Value("${mining.difficulty}")
    private static int MINING_DIFFICULTY = 4;

    private final BlockchainDomainService blockchainDomainService;

    private static BlockChain blockChainForTest;

    public BlockchainDomainServiceTest() {
        this.blockchainDomainService = new BlockchainDomainService();
    }

    @BeforeAll
    static void initBlockchain() {
        blockChainForTest = new BlockChain("BlockchainDomainServiceTest", MINING_DIFFICULTY);

        Block genesisBlock = Block.builder()
                .index(0)
                .timeStamp(LocalDateTime.now())
                .previousHash(null)
                .data("Genesis block")
                .build();
        blockChainForTest.addBlock(genesisBlock);
    }

    @BeforeEach
    void initTest() {
        blockChainForTest.emptyTheChain();
    }

    @Test
    void testIsValid() {
        blockChainForTest.addBlock(blockChainForTest.newBlock("this is the first block"));
        blockChainForTest.addBlock(blockChainForTest.newBlock("this is the second block"));

        assertThat(blockchainDomainService.isBlockchainValid(blockChainForTest)).isTrue();
    }

    @Test
    void testIsValidInvalidPreviousBlock() {
        blockChainForTest.addBlock(blockChainForTest.newBlock("this is the first block"));
        blockChainForTest.addBlock(Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 45, 00))
                .data("this is the second block")
                .build());

        assertThat(blockchainDomainService.isBlockchainValid(blockChainForTest)).isFalse();
    }
}