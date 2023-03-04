package com.septgrandcorsaire.blockchain.domain;

import com.septgrandcorsaire.blockchain.application.VoteQuery;
import com.septgrandcorsaire.blockchain.infrastructure.service.JsonService;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Value
public class VotingData implements Data {
    private String electionName;

    private String candidateName;

    private LocalDateTime votingDate;

    public static VotingData fromVoteQuery(VoteQuery query) {
        return new VotingData(
                query.getElectionName(),
                query.getCandidateName(),
                query.getVotingDate()
        );
    }

    @Override
    public String toString() {
        return JsonService.toJson(this);
    }
}
