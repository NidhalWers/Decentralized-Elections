package com.septgrandcorsaire.blockchain.api.error.exception;

public class ElectionNotFinishedException extends SmartVoteException {

    public ElectionNotFinishedException(String election) {
        super(String.format(ErrorCode.ELECTION_NOT_FINISHED.getDefaultMessage(), election),
                ErrorCode.ELECTION_NOT_FINISHED);
    }
}
