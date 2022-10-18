package com.septgrandcorsaire.blockchain.util.warning;


import com.septgrandcorsaire.blockchain.model.Block;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class WarningMessageService {
    public WarningMessageService() {
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