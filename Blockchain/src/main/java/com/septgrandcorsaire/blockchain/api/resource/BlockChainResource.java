package com.septgrandcorsaire.blockchain.api.resource;

import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.adapter.BlockchainDomainService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class BlockChainResource implements ElectionResource {

    private final String electionName;

    private final boolean isBlockchainValid;

    private final List<BlockResource> blocks;

    private final String apiKey;

    private BlockChainResource(final String electionName, final boolean isBlockchainValid, final List<BlockResource> blocks, String apiKey) {
        this.electionName = electionName;
        this.isBlockchainValid = isBlockchainValid;
        this.blocks = blocks;
        this.apiKey = apiKey;
    }

    public static BlockChainResource of(BlockChain blockChain, String apiKey) {
        return new BlockChainResource(blockChain.getName(), new BlockchainDomainService().isBlockchainValid(blockChain), blockChain.getBlocks().stream().map(block -> BlockResource.of(block)).collect(Collectors.toList()), apiKey);
    }

    public String getElectionName() {
        return electionName;
    }

    public boolean isBlockchainValid() {
        return isBlockchainValid;
    }

    public List<BlockResource> getBlocks() {
        return blocks;
    }

    public String getApiKey() {
        return apiKey;
    }
}
