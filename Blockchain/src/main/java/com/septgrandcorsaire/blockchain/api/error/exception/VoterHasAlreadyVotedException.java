package com.septgrandcorsaire.blockchain.api.error.exception;

public class VoterHasAlreadyVotedException extends RuntimeException {

    private ErrorCode code;

    public VoterHasAlreadyVotedException(String message) {
        super(message);
        this.code = ErrorCode.HAS_ALREADY_VOTED;
    }

    public ErrorCode getCode() {
        return code;
    }
}
