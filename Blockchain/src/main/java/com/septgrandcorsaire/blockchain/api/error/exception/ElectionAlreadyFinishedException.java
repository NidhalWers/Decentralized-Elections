package com.septgrandcorsaire.blockchain.api.error.exception;

import com.septgrandcorsaire.blockchain.domain.ElectionResult;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class ElectionAlreadyFinishedException extends RuntimeException {

    private ErrorCode code;

    private ElectionResult electionResult;

    public ElectionAlreadyFinishedException(String format) {
        super(format);
        this.code = ErrorCode.ELECTION_ALREADY_FINISHED;
    }

    public ErrorCode getCode() {
        return code;
    }

    public ElectionResult getElectionResult() {
        return electionResult;
    }

    public ElectionAlreadyFinishedException setElectionResult(ElectionResult electionResult) {
        this.electionResult = electionResult;
        return this;
    }
}
