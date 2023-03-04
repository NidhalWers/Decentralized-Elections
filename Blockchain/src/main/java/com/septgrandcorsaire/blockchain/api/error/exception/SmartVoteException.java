package com.septgrandcorsaire.blockchain.api.error.exception;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class SmartVoteException extends RuntimeException {

    private ErrorCode code;

    public SmartVoteException(String message, ErrorCode code) {
        super(message);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
