package com.septgrandcorsaire.blockchain.api.error.exception;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class InvalidApiKeyException extends RuntimeException {

    private ErrorCode code;

    public InvalidApiKeyException(String message) {
        super(message);
        this.code = ErrorCode.INVALID_API_KEY;
    }

    public ErrorCode getCode() {
        return code;
    }
}
