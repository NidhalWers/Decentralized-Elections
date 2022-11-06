package com.septgrandcorsaire.blockchain.api.payload;

import com.septgrandcorsaire.blockchain.api.error.exception.IllegalPayloadArgumentException;
import com.septgrandcorsaire.blockchain.application.VoteQuery;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class VotePayloadTest {

    @Test
    void toQuery() {
        VotePayload payload = VotePayload.builder()
                .electionName("test")
                .candidateName("one")
                .votingTime("2022-10-24T10:00:00")
                .voterId("fake_person1_id")
                .build();

        VoteQuery actualQuery = payload.toQuery();

        assertThat(actualQuery).isNotNull();
        assertThat(actualQuery.getElectionName()).isEqualTo("test");
        assertThat(actualQuery.getCandidateName()).isEqualTo("one");
        assertThat(actualQuery.getVotingDate().getYear()).isEqualTo(2022);
        assertThat(actualQuery.getVotingDate().getMonthValue()).isEqualTo(10);
        assertThat(actualQuery.getVotingDate().getDayOfMonth()).isEqualTo(24);
        assertThat(actualQuery.getVotingDate().getHour()).isEqualTo(10);
        assertThat(actualQuery.getVotingDate().getMinute()).isEqualTo(0);
    }

    @Test
    void toQueryNoElectionName() {
        VotePayload payload = VotePayload.builder()
                .electionName("")
                .candidateName("one")
                .votingTime("2022-10-24T10:00:00")
                .voterId("fake_person1_id")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter 'election_name' is required.");
    }

    @Test
    void toQueryNoVoterId() {
        VotePayload payload = VotePayload.builder()
                .electionName("test")
                .candidateName("")
                .votingTime("2022-10-24T10:00:00")
                .voterId("")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter 'voter_id' is required.");
    }

    @Test
    void toQueryNoVotingTime() {
        VotePayload payload = VotePayload.builder()
                .electionName("test")
                .candidateName("one")
                .votingTime("")
                .voterId("fake_person1_id")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter 'voting_time' is required.");
    }

    @Test
    void toQueryBadFormatVotingTime() {
        VotePayload payload = VotePayload.builder()
                .electionName("test")
                .candidateName("one")
                .votingTime("2022/10/24T10:00:00")
                .voterId("fake_person1_id")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("The 'voting_time' parameter should be in [YY-MM-DD'T'HH:mm::ss] format");
    }


}