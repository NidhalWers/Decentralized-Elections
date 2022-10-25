package com.septgrandcorsaire.blockchain.api.payload;

import com.septgrandcorsaire.blockchain.api.error.exception.IllegalPayloadArgumentException;
import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElectionPayloadTest {

    @Test
    void toQuery() {
        ElectionPayload payload = ElectionPayload.builder()
                .candidates(List.of("one", "two"))
                .startingDate("2022-10-24T10:00:00")
                .closingDate("2022-10-25T21:00")
                .electionName("test")
                .build();

        ElectionQuery actualResult = payload.toQuery();

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getCandidates()).containsExactly("one", "two");
        assertThat(actualResult.getStartingDate().getYear()).isEqualTo(2022);
        assertThat(actualResult.getStartingDate().getMonthValue()).isEqualTo(10);
        assertThat(actualResult.getStartingDate().getDayOfMonth()).isEqualTo(24);
        assertThat(actualResult.getStartingDate().getHour()).isEqualTo(10);
        assertThat(actualResult.getStartingDate().getMinute()).isEqualTo(0);
        assertThat(actualResult.getElectionName()).isEqualTo("test");
        assertThat(actualResult.getClosingDate().getYear()).isEqualTo(2022);
        assertThat(actualResult.getClosingDate().getMonthValue()).isEqualTo(10);
        assertThat(actualResult.getClosingDate().getDayOfMonth()).isEqualTo(25);
        assertThat(actualResult.getClosingDate().getHour()).isEqualTo(21);
        assertThat(actualResult.getClosingDate().getMinute()).isEqualTo(0);
    }

    @Test
    void toQueryNoElectionName() {
        ElectionPayload payload = ElectionPayload.builder()
                .candidates(List.of("one", "two"))
                .startingDate("2022-10-24T10:00:00")
                .closingDate("2022-10-25T21:00")
                .electionName("")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter 'election_name' is required.");
    }

    @Test
    void toQueryNoStartingDate() {
        ElectionPayload payload = ElectionPayload.builder()
                .candidates(List.of("one", "two"))
                .startingDate("")
                .closingDate("2022-10-25T21:00")
                .electionName("test")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter 'starting_date' is required.");
    }

    @Test
    void toQueryNoClosingDate() {
        ElectionPayload payload = ElectionPayload.builder()
                .candidates(List.of("one", "two"))
                .startingDate("2022-10-24T10:00:00")
                .closingDate("")
                .electionName("test")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter 'closing_date' is required.");
    }

    @Test
    void toQueryNoCandidates() {
        ElectionPayload payload = ElectionPayload.builder()
                .candidates(List.of())
                .startingDate("2022-10-24T10:00:00")
                .closingDate("2022-10-25T21:00")
                .electionName("test")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("Parameter 'candidates' is required.");
    }

    @Test
    void toQueryBadFormatStartingTime() {
        ElectionPayload payload = ElectionPayload.builder()
                .candidates(List.of("one", "two"))
                .startingDate("2022/10/24T10:00:00")
                .closingDate("2022-10-25T21:00")
                .electionName("test")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("The 'starting_date' parameter should be in [YY-MM-DD'T'HH:mm::ss] format");
    }

    @Test
    void toQueryBadFormatClosingTime() {
        ElectionPayload payload = ElectionPayload.builder()
                .candidates(List.of("one", "two"))
                .startingDate("2022-10-24T10:00:00")
                .closingDate("2022/10/25T21:00")
                .electionName("test")
                .build();

        IllegalPayloadArgumentException exception = assertThrows(IllegalPayloadArgumentException.class, () -> {
            payload.toQuery();
        });

        assertThat(exception.getMessage()).isEqualTo("The 'closing_date' parameter should be in [YY-MM-DD'T'HH:mm::ss] format");
    }
}