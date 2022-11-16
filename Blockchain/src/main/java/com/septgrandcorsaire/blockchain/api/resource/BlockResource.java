package com.septgrandcorsaire.blockchain.api.resource;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.Data;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class BlockResource {

    private final String hash;

    private final String previousHash;

    private final Data data;

    private BlockResource(final String hash, final String previousHash, final Data data) {
        this.hash = hash;
        this.previousHash = previousHash;
        this.data = data;
    }

    public static BlockResource of(Block block) {
        return new BlockResource(block.getHash(), block.getPreviousHash(), block.getData());
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public Data getData() {
        return data;
    }
}
