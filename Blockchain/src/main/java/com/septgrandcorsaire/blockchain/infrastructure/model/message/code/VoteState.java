package com.septgrandcorsaire.blockchain.infrastructure.model.message.code;

public enum VoteState {
    VOTE_FOUND,

    VOTE_NOT_FOUND;

    public boolean isNotFound() {
        return !this.equals(VOTE_FOUND);
    }
}
