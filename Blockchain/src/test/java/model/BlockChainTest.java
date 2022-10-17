package model;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BlockChainTest {

    @BeforeAll
    static void initTest(){
        BlockChain.BLOCK_CHAIN.emptyTheChain();
    }

    @Test
    void getBlockChain() {
       List list =  BlockChain.BLOCK_CHAIN.getBlockChain();

       assertThat(list).isInstanceOf(List.class);
    }

    @Test
    void addBlock() {
        BlockChain.BLOCK_CHAIN.addBlock(Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the second block")
                .build());

        assertThat(BlockChain.BLOCK_CHAIN.getBlockChain()).hasSize(1);
        assertThat(BlockChain.BLOCK_CHAIN.getBlockChain().get(0).getData()).isEqualTo("this is the second block");
    }

    @Test
    void emptyTheChain() {
        BlockChain.BLOCK_CHAIN.emptyTheChain();

        assertThat(BlockChain.BLOCK_CHAIN.getBlockChain()).isEmpty();
    }
}