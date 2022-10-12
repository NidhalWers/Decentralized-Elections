package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BlockChainTest {

    @BeforeEach
    void initTest(){
        BlockChain.BLOCK_CHAIN.emptyTheChain();
    }

    @Test
    void testGetBlockChain() {
       List list =  BlockChain.BLOCK_CHAIN.getBlockChain();

       assertThat(list).isInstanceOf(List.class);
    }

    @Test
    void testAddBlock() {
       BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the second block")
                .build());

        assertThat(BlockChain.BLOCK_CHAIN.getBlockChain()).hasSize(1);
        assertThat(BlockChain.BLOCK_CHAIN.getBlockChain().get(0).getData()).isEqualTo("this is the second block");
    }

    @Test
    void testEmptyTheChain() {
        BlockChain.BLOCK_CHAIN.emptyTheChain();

        assertThat(BlockChain.BLOCK_CHAIN.getBlockChain()).isEmpty();
    }

    @Test
    void testIsValid() {
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash("racine")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the first block")
                .build());
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash(BlockChain.BLOCK_CHAIN.getBlock(0).computeHash())
                .timeStamp(LocalDateTime.of(2022,10,30,8,45,00))
                .data("this is the second block")
                .build());

        assertThat(BlockChain.BLOCK_CHAIN.isValid()).isTrue();
    }

    @Test
    void testIsValidInvalidPreviousBlock() {
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

        assertThat(BlockChain.BLOCK_CHAIN.isValid()).isFalse();
    }
}