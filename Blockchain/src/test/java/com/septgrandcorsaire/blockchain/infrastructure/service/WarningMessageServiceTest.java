package com.septgrandcorsaire.blockchain.infrastructure.service;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.util.warning.WarningMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
class WarningMessageServiceTest {
    private static final int MINING_DIFFICULTY = 4;

    private BlockChain blockChainForTest = new BlockChain("WarningMessageServiceTest", MINING_DIFFICULTY);


    @BeforeEach
    void initTest() {
        blockChainForTest.emptyTheChain();
    }

    @Test
    void testCreateInvalidBlockHashMessage() {
        Block block = Block.builder()
                .previousHash("racine")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 30, 00))
                .data("this is the second block")
                .build();

        WarningMessageService warningMessageService = new WarningMessageService();
        String expectedErrorMessage = "the block identified by #0, " +
                "with the previous hash : 'racine', " +
                "should have the hash : '" + block.computeHash() + "' instead";
        String actualErrorMessage = warningMessageService.createInvalidBlockHashMessage(block);

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void testCreateInvalidPreviousHash() {
        blockChainForTest.addBlock(blockChainForTest.newBlock("this is the first block"));
        blockChainForTest.addBlock(Block.builder()
                .index(2)
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 45, 00))
                .data("this is the second block")
                .build());

        WarningMessageService warningMessageService = new WarningMessageService();
        String expectedErrorMessage = "the block identified by #2, " +
                "with the previous hash : 'hash-1', " +
                "should have the previous hash : '" + blockChainForTest.getBlock(1).mineBlock(MINING_DIFFICULTY).getHash() + "' instead";
        String actualErrorMessage = warningMessageService.createInvalidPreviousHashMessage(
                blockChainForTest.getBlock(2),
                blockChainForTest.getBlock(1)
        );

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void testCreateInvalidIndexesMessage() {
        blockChainForTest.addBlock(blockChainForTest.newBlock("this is the first block"));
        blockChainForTest.addBlock(Block.builder()
                .index(3)
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 45, 00))
                .data("this is the second block")
                .build());

        WarningMessageService warningMessageService = new WarningMessageService();
        String expectedErrorMessage = "the block identified by #3, " +
                "is chain-linked to the block #1";
        String actualErrorMessage = warningMessageService.createInvalidIndexesMessage(
                blockChainForTest.getBlock(2),
                blockChainForTest.getBlock(1)
        );

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }
}