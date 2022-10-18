package com.septgrandcorsaire.blockchain;

import com.septgrandcorsaire.blockchain.model.BlockChain;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class Main {

    public static void main(String[] args) {
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("Premier Block"));
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("On test"));
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("Il faut de la data"));
        BlockChain.BLOCK_CHAIN.addBlock(BlockChain.BLOCK_CHAIN.newBlock("M1APP-BDML"));

        System.out.println("Is blockchain valid ? " + BlockChain.BLOCK_CHAIN.isBlockchainValid());

        BlockChain.BLOCK_CHAIN.printBlockchain();
    }
}
