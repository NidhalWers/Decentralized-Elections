package com.septgrandcorsaire.blockchain.api.error.exception;

public class VoterHasAlreadyVotedException extends SmartVoteException {

    public VoterHasAlreadyVotedException(String voterId, String electionName) {
        super(String.format(ErrorCode.HAS_ALREADY_VOTED.getDefaultMessage(), voterId, electionName),
                ErrorCode.HAS_ALREADY_VOTED);
    }
}
