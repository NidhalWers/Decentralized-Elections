package service;

import model.Block;

public class ErrorMessageService {
    public ErrorMessageService() {
    }

    public String createInvalidBlockHashMessage(Block block) {
        return "the block identified by '" + block.getHash() + "', " +
                "with the previous hash : '" + block.getPreviousHash() + "', " +
                "should have the hash : '" + block.computeHash() + "' instead";
    }

    public String createInvalidPreviousHash(Block block, Block previousBlock) {
        return "the block identified by '" + block.getHash() + "' " +
                "with the previous hash : '" + block.getPreviousHash() + "', " +
                "should have the previous hash : '" + previousBlock.getHash() + "' instead";
    }
}