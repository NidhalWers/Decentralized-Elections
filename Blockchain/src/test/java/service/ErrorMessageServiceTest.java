package service;

import model.Block;
import model.BlockChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.error.ErrorMessageService;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ErrorMessageServiceTest {

    @BeforeEach
    void initTest(){
        BlockChain.BLOCK_CHAIN.emptyTheChain();
    }

    @Test
    void testCreateInvalidBlockHashMessage() {
        Block block = Block.builder()
                .previousHash("racine")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the second block")
                .build();

        ErrorMessageService errorMessageService = new ErrorMessageService();
        String expectedErrorMessage = "the block identified by #0, "+
                "with the previous hash : 'racine', " +
                "should have the hash : '"+ block.computeHash() +"' instead";
        String actualErrorMessage = errorMessageService.createInvalidBlockHashMessage(block);

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void testCreateInvalidPreviousHash() {
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("this is the first block"));
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .index(2)
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,45,00))
                .data("this is the second block")
                .build());

        ErrorMessageService errorMessageService = new ErrorMessageService();
        String expectedErrorMessage = "the block identified by #2, " +
                "with the previous hash : 'hash-1', " +
                "should have the previous hash : '"+ BlockChain.BLOCK_CHAIN.getBlock(1).mineBlock(BlockChain.MINING_DIFFICULTY).getHash() +"' instead";
        String actualErrorMessage = errorMessageService.createInvalidPreviousHashMessage(
                BlockChain.BLOCK_CHAIN.getBlock(2),
                BlockChain.BLOCK_CHAIN.getBlock(1)
        );

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void testCreateInvalidIndexesMessage(){
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("this is the first block"));
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .index(3)
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,45,00))
                .data("this is the second block")
                .build());

        ErrorMessageService errorMessageService = new ErrorMessageService();
        String expectedErrorMessage = "the block identified by #3, " +
                "is chain-linked to the block #1";
        String actualErrorMessage = errorMessageService.createInvalidIndexesMessage(
                BlockChain.BLOCK_CHAIN.getBlock(2),
                BlockChain.BLOCK_CHAIN.getBlock(1)
        );

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }
}