package com.septgrandcorsaire.blockchain.api.error.exception;

public class ElectionNotFinishedException extends RuntimeException {
    private ErrorCode code;

    public ElectionNotFinishedException(String election) {
        super(String.format(ErrorCode.ELECTION_NOT_FINISHED.getDefaultMessage(), election));
        this.code = ErrorCode.ELECTION_NOT_FINISHED;
    }

    public ErrorCode getCode() {
        return code;
    }
}
