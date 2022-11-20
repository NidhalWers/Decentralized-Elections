package com.septgrandcorsaire.blockchain.infrastructure.model.message;

import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.code.ElectionState;

public class MessageOngoingElection implements MessageElectionResult {

    public final BlockChain blockChain;

    public final String status;

    private MessageOngoingElection(Builder builder) {
        this.blockChain = builder.blockChain;
        this.status = builder.status;
    }

    @Override
    public ElectionState code() {
        return ElectionState.ONGOING;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private BlockChain blockChain;

        private String status;

        public Builder blockChain(BlockChain blockChain) {
            this.blockChain = blockChain;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public MessageOngoingElection build() {
            return new MessageOngoingElection(this);
        }
    }
}
