package com.septgrandcorsaire.blockchain.api.error.exception;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class InvalidApiKeyException extends SmartVoteException {

    public InvalidApiKeyException(String message) {
        super(message,
                ErrorCode.INVALID_API_KEY);
    }
}
