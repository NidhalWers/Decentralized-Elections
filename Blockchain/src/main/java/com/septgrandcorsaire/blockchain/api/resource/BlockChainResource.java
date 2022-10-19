package com.septgrandcorsaire.blockchain.api.resource;

import com.septgrandcorsaire.blockchain.domain.Block;

import java.util.List;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class BlockChainResource {

    private String name;

    private List<Block> blocks;

    private BlockChainResource(String name, List<Block> blocks) {
        this.name = name;
        this.blocks = blocks;
    }

    public static BlockChainResource of(String name, List<Block> blocks) {
        return new BlockChainResource(name, blocks);
    }

    public String getName() {
        return name;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
