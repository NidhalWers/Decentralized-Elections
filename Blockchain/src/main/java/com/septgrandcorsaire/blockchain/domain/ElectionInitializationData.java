package com.septgrandcorsaire.blockchain.domain;

import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.infrastructure.service.JsonService;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@lombok.Data
@AllArgsConstructor
public class ElectionInitializationData implements Data {
    private List<String> candidates;

    private LocalDateTime startingDate;

    private LocalDateTime closingDate;

    private String electionName;

    public static ElectionInitializationData fromElectionQuery(ElectionQuery query) {
        if (query.isBlankVotesCounted()) {
            List<String> newList = new ArrayList<>();
            newList.add("blank_votes");
            newList.addAll(query.getCandidates());
            query.setCandidates(newList);
        }
        return new ElectionInitializationData(
                query.getCandidates(),
                query.getStartingDate(),
                query.getClosingDate(),
                query.getElectionName()
        );
    }

    @Override
    public String toString() {
        return JsonService.toJson(this);
    }
}
