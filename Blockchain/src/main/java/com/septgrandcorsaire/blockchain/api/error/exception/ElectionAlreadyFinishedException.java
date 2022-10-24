package com.septgrandcorsaire.blockchain.api.error.exception;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class ElectionAlreadyFinishedException extends RuntimeException {

    private ErrorCode code;

    public ElectionAlreadyFinishedException(String format) {
        super(format);
        this.code = ErrorCode.ELECTION_ALREADY_FINISHED;
    }

    public ErrorCode getCode() {
        return code;
    }
}
