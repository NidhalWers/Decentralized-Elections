package com.septgrandcorsaire.blockchain.api.error.exception;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class ElectionNotStartedException extends RuntimeException {

    private ErrorCode code;

    public ElectionNotStartedException(String message) {
        super(message);
        this.code = ErrorCode.ELECTION_NOT_STARTED;
    }

    public ErrorCode getCode() {
        return code;
    }
}
