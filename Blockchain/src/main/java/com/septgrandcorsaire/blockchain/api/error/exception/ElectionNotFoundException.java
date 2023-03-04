package com.septgrandcorsaire.blockchain.api.error.exception;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class ElectionNotFoundException extends SmartVoteException {

    public ElectionNotFoundException(String electionName) {
        super(String.format(ErrorCode.NOT_FOUND_ELECTION.getDefaultMessage(), electionName),
                ErrorCode.NOT_FOUND_ELECTION);
    }
}
