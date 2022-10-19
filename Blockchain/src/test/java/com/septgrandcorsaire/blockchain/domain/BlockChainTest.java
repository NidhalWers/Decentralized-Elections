package com.septgrandcorsaire.blockchain.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
class BlockChainTest {
    
    @Value("${mining.difficulty}")
    private static int MINING_DIFFICULTY;

    private BlockChain blockChainForTest = new BlockChain(MINING_DIFFICULTY);

    @BeforeEach
    void initTest() {
        blockChainForTest.emptyTheChain();
    }

    @Test
    void testGetBlockChain() {
        List list = blockChainForTest.getBlocks();

        assertThat(list).isInstanceOf(List.class);
    }

    @Test
    void testAddBlock() {
        blockChainForTest.addBlock(Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 30, 00))
                .data("this is the second block")
                .build());

        assertThat(blockChainForTest.getBlocks()).hasSize(2);
        assertThat(blockChainForTest.getBlocks().get(1).getData()).isEqualTo("this is the second block");
    }

    @Test
    void testEmptyTheChain() {
        blockChainForTest.emptyTheChain();

        assertThat(blockChainForTest.getBlocks()).hasSize(1);
        assertThat(blockChainForTest.getLatestBlock().getData()).isEqualTo("Genesis block");
    }
}