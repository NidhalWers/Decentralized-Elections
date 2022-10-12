package service;

import model.Block;
import model.BlockChain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ErrorMessageServiceTest {

    @BeforeEach
    void initTest(){
        BlockChain.BLOCK_CHAIN.emptyTheChain();
    }

    @Test
    void createInvalidBlockHashMessage() {
        Block block = Block.builder()
                .previousHash("racine")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the second block")
                .build();

        ErrorMessageService errorMessageService = new ErrorMessageService();
        String expectedErrorMessage = "the block identified by '" + block.getHash() + "', "+
                "with the previous hash : 'racine', " +
                "should have the hash : '190f023f90cbf3b148d4730a0a29b00556b8228698ac962ad7ddbbc3c1f9e91013f837285f3dd1d0dcedf3ac52ed1e26718f89648cb99050a7b56625dadca13c' instead";
        String actualErrorMessage = errorMessageService.createInvalidBlockHashMessage(block);

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }

    @Test
    void createInvalidPreviousHash() {
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash("racine")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the first block")
                .build());
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,45,00))
                .data("this is the second block")
                .build());

        ErrorMessageService errorMessageService = new ErrorMessageService();
        String expectedErrorMessage = "the block identified by '424b0ba0f29ec2745c1dfbf471a10f670fc259c410ed038e0278e2ed42bf942018e1c4394a951147c078e8340b5b212478253df0b53c3055194488ec2e8a93a1' " +
                "with the previous hash : 'hash-1', " +
                "should have the previous hash : '5137ce2941d033006e75bb4b6e207822a050c55acccf81b6173cd8d7b9effce2871989a318effdd2cfc3bdae0cd599a443ad7b72c6b56062da1a3abdcafcabb2' instead";
        String actualErrorMessage = errorMessageService.createInvalidPreviousHash(
                BlockChain.BLOCK_CHAIN.getBlock(1),
                BlockChain.BLOCK_CHAIN.getBlock(0)
        );

        assertThat(actualErrorMessage).isEqualTo(expectedErrorMessage);
    }
}