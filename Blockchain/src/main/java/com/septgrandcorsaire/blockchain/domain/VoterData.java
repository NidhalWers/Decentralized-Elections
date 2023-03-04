package com.septgrandcorsaire.blockchain.domain;

import com.septgrandcorsaire.blockchain.application.VoteQuery;
import com.septgrandcorsaire.blockchain.infrastructure.service.JsonService;
import lombok.Value;

@Value
public class VoterData implements Data {
    private String voterId;

    public static VoterData fromVoteQuery(VoteQuery query) {
        return new VoterData(
                query.getVoterId()
        );
    }

    @Override
    public String toString() {
        return JsonService.toJson(this);
    }
}
