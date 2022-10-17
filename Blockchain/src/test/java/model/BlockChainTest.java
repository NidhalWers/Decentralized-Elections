package model;

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
       List list =  BlockChain.BLOCK_CHAIN.getBlocks();

       assertThat(list).isInstanceOf(List.class);
    }

    @Test
    void testAddBlock() {
       BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the second block")
                .build());

        assertThat(BlockChain.BLOCK_CHAIN.getBlocks()).hasSize(2);
        assertThat(BlockChain.BLOCK_CHAIN.getBlocks().get(1).getData()).isEqualTo("this is the second block");
    }

    @Test
    void testEmptyTheChain() {
        BlockChain.BLOCK_CHAIN.emptyTheChain();

        assertThat(BlockChain.BLOCK_CHAIN.getBlocks()).hasSize(1);
        assertThat(BlockChain.BLOCK_CHAIN.getLatestBlock().getData()).isEqualTo("Genesis block");
    }

    @Test
    void testIsValid() {
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("this is the first block"));
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("this is the second block"));

        assertThat(BlockChain.BLOCK_CHAIN.isBlockchainValid()).isTrue();
    }

    @Test
    void testIsValidInvalidPreviousBlock() {
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("this is the first block"));
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,45,00))
                .data("this is the second block")
                .build());

        assertThat(BlockChain.BLOCK_CHAIN.isBlockchainValid()).isFalse();
    }
}