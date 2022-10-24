package com.septgrandcorsaire.blockchain.api.error.exception;

public class ElectionNotFoundException extends RuntimeException {

    private ErrorCode code;

    public ElectionNotFoundException(String s) {
        super(s);
        this.code = ErrorCode.NOT_FOUND_ELECTION;
    }

    public ErrorCode getCode() {
        return code;
    }
}
