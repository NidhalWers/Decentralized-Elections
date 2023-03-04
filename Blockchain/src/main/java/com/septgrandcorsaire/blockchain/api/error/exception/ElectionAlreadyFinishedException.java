package com.septgrandcorsaire.blockchain.api.error.exception;

import com.septgrandcorsaire.blockchain.domain.ElectionResult;

import java.time.LocalDateTime;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class ElectionAlreadyFinishedException extends SmartVoteException {

    private ElectionResult electionResult;

    public ElectionAlreadyFinishedException(String electionName, LocalDateTime closingDate) {
        super(String.format(ErrorCode.ELECTION_ALREADY_FINISHED.getDefaultMessage(), electionName, closingDate),
                ErrorCode.ELECTION_ALREADY_FINISHED);
    }

    public ElectionResult getElectionResult() {
        return electionResult;
    }

    public ElectionAlreadyFinishedException setElectionResult(ElectionResult electionResult) {
        this.electionResult = electionResult;
        return this;
    }
}
