package com.septgrandcorsaire.blockchain.domain;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Value
@Builder
public class ElectionResult {
    private String electionName;

    private LocalDateTime startingDate;

    private LocalDateTime closingDate;

    private Map<String, Long> candidatesResults;
}
