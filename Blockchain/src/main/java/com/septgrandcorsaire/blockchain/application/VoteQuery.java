package com.septgrandcorsaire.blockchain.application;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Value
@Builder
public class VoteQuery {
    private String electionName;

    private String candidateName;

    private LocalDateTime votingDate;

    public VoteQuery setCandidateName(String input) {
        return VoteQuery.builder()
                .electionName(electionName)
                .candidateName(input)
                .votingDate(votingDate)
                .build();
    }
}
