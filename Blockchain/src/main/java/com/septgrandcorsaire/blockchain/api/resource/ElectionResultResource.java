package com.septgrandcorsaire.blockchain.api.resource;

import com.septgrandcorsaire.blockchain.domain.ElectionResult;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class ElectionResultResource implements ElectionResource {

    private final String electionName;

    private final LocalDateTime startingDate;

    private final LocalDateTime closingDate;

    private final Map<String, Long> candidatesResults;

    private ElectionResultResource(final String electionName, final LocalDateTime startingDate, final LocalDateTime closingDate, Map<String, Long> candidatesResults) {
        this.electionName = electionName;
        this.startingDate = startingDate;
        this.closingDate = closingDate;
        this.candidatesResults = candidatesResults;
    }

    public static ElectionResultResource of(final ElectionResult electionResult) {
        return new ElectionResultResource(
                electionResult.getElectionName(),
                electionResult.getStartingDate(),
                electionResult.getClosingDate(),
                electionResult.getCandidatesResults()
        );
    }

    public String getElectionName() {
        return electionName;
    }

    public LocalDateTime getStartingDate() {
        return startingDate;
    }

    public LocalDateTime getClosingDate() {
        return closingDate;
    }

    public Map<String, Long> getCandidatesResults() {
        return candidatesResults;
    }
}
