package com.septgrandcorsaire.blockchain.api.payload;

import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectionPayload {

    private List<String> candidates;

    private String startingDate;

    private String closingDate;

    private String electionName;

    public ElectionQuery toQuery() {
        validatePayload();
        return ElectionQuery.builder()
                .candidates(this.candidates)
                .startingDate(LocalDateTime.parse(this.startingDate))
                .closingDate(LocalDateTime.parse(this.closingDate))
                .electionName(this.electionName)
                .build();
    }

    private void validatePayload() {
        if (electionName == null || electionName.isBlank()) {
            throw new IllegalArgumentException("election"); //todo create my own exception
        }
        if (startingDate == null || startingDate.isBlank()) {
            throw new IllegalArgumentException("startingDate");
        }
        if (closingDate == null || closingDate.isBlank()) {
            throw new IllegalArgumentException("closingDate");
        }
        if (candidates == null || candidates.isEmpty()) {
            throw new IllegalArgumentException("candidates");
        }

    }
}
