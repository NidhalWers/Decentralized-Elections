package com.septgrandcorsaire.blockchain.api.resource;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.BlockchainDomainService;

import java.util.List;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class BlockChainResource {

    private final String name;

    private final boolean isBlockchainValid;

    private final List<Block> blocks;

    private BlockChainResource(final String name, final boolean isBlockchainValid, final List<Block> blocks) {
        this.name = name;
        this.isBlockchainValid = isBlockchainValid;
        this.blocks = blocks;
    }

    public static BlockChainResource of(BlockChain blockChain) {
        return new BlockChainResource(blockChain.getName(), new BlockchainDomainService().isBlockchainValid(blockChain), blockChain.getBlocks());
    }

    public String getName() {
        return name;
    }

    public boolean isBlockchainValid() {
        return isBlockchainValid;
    }

    public List<Block> getBlocks() {
        return blocks;
    }
}
