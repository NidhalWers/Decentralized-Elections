package com.septgrandcorsaire.blockchain.infrastructure.model.message;

import com.septgrandcorsaire.blockchain.domain.BlockChain;

public class MessageBlockchainCreated implements Message {

    public final BlockChain blockChain;

    public final String apiKey;

    public final String electionStatus;

    private MessageBlockchainCreated(BlockChain blockChain, String apiKey, String electionStatus) {
        this.blockChain = blockChain;
        this.apiKey = apiKey;
        this.electionStatus = electionStatus;
    }

    public static MessageBlockchainCreated of(BlockChain blockChain, String apiKey, String electionStatus) {
        return new MessageBlockchainCreated(blockChain, apiKey, electionStatus);
    }

}
