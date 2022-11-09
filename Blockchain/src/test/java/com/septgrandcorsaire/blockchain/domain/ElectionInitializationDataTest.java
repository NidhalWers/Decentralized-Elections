package com.septgrandcorsaire.blockchain.domain;

import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ElectionInitializationDataTest {

    private static final LocalDateTime endingTestDateTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);

    private static final LocalDateTime startingTestDateTime = LocalDateTime.of(2022, 10, 24, 10, 0, 0);

    @Test
    void fromElectionQueryBlankVotes() {
        ElectionQuery querySecondElection = ElectionQuery.builder()
                .electionName("second_election")
                .startingDate(startingTestDateTime)
                .closingDate(endingTestDateTime)
                .candidates(List.of("Henry", "Julius", "Mohammed"))
                .blankVotesCounted(true)
                .build();

        ElectionInitializationData actual = ElectionInitializationData.fromElectionQuery(querySecondElection);

        assertThat(actual).isNotNull();
    }
}