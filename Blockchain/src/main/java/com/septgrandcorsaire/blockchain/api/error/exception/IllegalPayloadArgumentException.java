package com.septgrandcorsaire.blockchain.api.error.exception;

public class IllegalPayloadArgumentException extends IllegalArgumentException {

    private ErrorCode code;

    public IllegalPayloadArgumentException(ErrorCode code, String s) {
        super(s);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
