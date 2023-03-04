package com.septgrandcorsaire.blockchain.api.error.exception;

import java.time.LocalDateTime;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class ElectionNotStartedException extends SmartVoteException {

    public ElectionNotStartedException(String electionName, LocalDateTime startingDate) {
        super(String.format(ErrorCode.ELECTION_NOT_STARTED.getDefaultMessage(), electionName, startingDate),
                ErrorCode.ELECTION_NOT_STARTED);
    }
}
