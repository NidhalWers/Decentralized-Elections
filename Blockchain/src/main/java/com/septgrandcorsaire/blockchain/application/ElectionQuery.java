package com.septgrandcorsaire.blockchain.application;

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

    private boolean blankVotesCounted;
}
