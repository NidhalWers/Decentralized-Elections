package com.septgrandcorsaire.blockchain.util.error;


import com.septgrandcorsaire.blockchain.model.Block;

public class ErrorMessageService {
    public ErrorMessageService() {
    }

    public String createInvalidBlockHashMessage(Block block) {
        return "the block identified by #" + block.getIndex() + ", " +
                "with the previous hash : '" + block.getPreviousHash() + "', " +
                "should have the hash : '" + block.computeHash() + "' instead";
    }

    public String createInvalidPreviousHashMessage(Block block, Block previousBlock) {
        return "the block identified by #" + block.getIndex() + ", " +
                "with the previous hash : '" + block.getPreviousHash() + "', " +
                "should have the previous hash : '" + previousBlock.getHash() + "' instead";
    }

    public String createInvalidIndexesMessage(Block block, Block previousBlock) {
        return "the block identified by #" + block.getIndex() + ", " +
                "is chain-linked to the block #" + previousBlock.getIndex();
    }
}