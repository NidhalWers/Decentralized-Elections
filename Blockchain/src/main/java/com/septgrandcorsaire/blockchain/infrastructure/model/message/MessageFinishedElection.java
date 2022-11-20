package com.septgrandcorsaire.blockchain.infrastructure.model.message;

import com.septgrandcorsaire.blockchain.domain.ElectionResult;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.code.ElectionState;

public class MessageFinishedElection implements MessageElectionResult {

    public final ElectionResult electionResult;

    public final String status;

    private MessageFinishedElection(Builder builder) {
        this.electionResult = builder.electionResult;
        this.status = builder.status;
    }

    @Override
    public ElectionState code() {
        return ElectionState.FINISHED;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private ElectionResult electionResult;

        private String status;

        public Builder electionResult(ElectionResult electionResult) {
            this.electionResult = electionResult;
            return this;
        }

        public Builder status(String status) {
            this.status = status;
            return this;
        }

        public MessageFinishedElection build() {
            return new MessageFinishedElection(this);
        }
    }
}
