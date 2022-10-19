package com.septgrandcorsaire.blockchain.infrastructure;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class BlockchainDomainServiceTest {

    @Value("${mining.difficulty}")
    private static int MINING_DIFFICULTY = 4;

    private final BlockchainDomainService blockchainDomainService;

    private BlockChain blockChainForTest;

    public BlockchainDomainServiceTest() {
        this.blockchainDomainService = new BlockchainDomainService();
        blockChainForTest = new BlockChain(MINING_DIFFICULTY);
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