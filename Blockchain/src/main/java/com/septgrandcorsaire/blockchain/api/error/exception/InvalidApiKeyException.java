package com.septgrandcorsaire.blockchain.api.error.exception;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class InvalidApiKeyException extends SmartVoteException {

    public InvalidApiKeyException(String electionName) {
        super(String.format(ErrorCode.INVALID_API_KEY.getDefaultMessage(), electionName),
                ErrorCode.INVALID_API_KEY);
    }
}
