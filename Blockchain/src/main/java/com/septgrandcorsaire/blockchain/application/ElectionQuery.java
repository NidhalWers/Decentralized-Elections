package com.septgrandcorsaire.blockchain.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.septgrandcorsaire.blockchain.api.configuration.JacksonConfiguration;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Value
@Builder
public class ElectionQuery {

    private List<String> candidates;

    private LocalDateTime startingDate;

    private LocalDateTime closingDate;

    private String electionName;

    public String toJson() {
        ObjectMapper objectMapper = new JacksonConfiguration().objectMapper();
        try {
            return objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
