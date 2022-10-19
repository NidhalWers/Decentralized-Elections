package com.septgrandcorsaire.blockchain;

import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.BlockchainDomainService;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class Main {

    private static BlockchainDomainService blockchainDomainService;

    public static void main(String[] args) {
        BlockChain blockChain = new BlockChain("Test", 4);
        blockChain.addBlock(blockChain.newBlock("Premier Block"));
        blockChain.addBlock(blockChain.newBlock("On test"));
        blockChain.addBlock(blockChain.newBlock("Il faut de la data"));
        blockChain.addBlock(blockChain.newBlock("M1APP-BDML"));

        System.out.println("Is blockchain valid ? " + blockchainDomainService.isBlockchainValid(blockChain));

        blockChain.printBlockchain();
    }
}
