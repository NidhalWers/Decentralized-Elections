package com.septgrandcorsaire.blockchain.infrastructure.model.message;

import com.septgrandcorsaire.blockchain.domain.BlockChain;

public class MessageBlockchainCreated implements Message {

    public final BlockChain blockChain;

    public final String apiKey;

    private MessageBlockchainCreated(BlockChain blockChain, String apiKey) {
        this.blockChain = blockChain;
        this.apiKey = apiKey;
    }

    public static MessageBlockchainCreated of(BlockChain blockChain, String apiKey) {
        return new MessageBlockchainCreated(blockChain, apiKey);
    }
}
