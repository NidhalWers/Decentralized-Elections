package com.septgrandcorsaire.blockchain.api.error.exception;

public class VoteNotFoundWithThisHashException extends RuntimeException {
    private ErrorCode code;

    public VoteNotFoundWithThisHashException(String election, String vote) {
        super(String.format(ErrorCode.VOTE_NOT_FOUND.getDefaultMessage(), election, vote));
        this.code = ErrorCode.VOTE_NOT_FOUND;
    }

    public ErrorCode getCode() {
        return code;
    }
}
