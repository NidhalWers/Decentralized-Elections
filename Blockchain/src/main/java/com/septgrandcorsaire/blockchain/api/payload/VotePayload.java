package com.septgrandcorsaire.blockchain.api.payload;

import com.septgrandcorsaire.blockchain.api.error.exception.ErrorCode;
import com.septgrandcorsaire.blockchain.api.error.exception.IllegalPayloadArgumentException;
import com.septgrandcorsaire.blockchain.application.VoteQuery;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.septgrandcorsaire.blockchain.infrastructure.util.DateTimeParser.parseDateTime;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VotePayload {
    private String electionName;

    private String candidateName;

    private String votingTime;

    public VoteQuery toQuery() {
        validatePayload();
        return VoteQuery.builder()
                .electionName(electionName)
                .candidateName(candidateName)
                .votingDate(parseVotingDate())
                .build();
    }

    private LocalDateTime parseVotingDate() {
        return parseDateTime(this.votingTime, "voting_time");
    }

    private void validatePayload() {
        if (electionName == null || electionName.isBlank()) {
            throw new IllegalPayloadArgumentException(ErrorCode.REQUIRED_PARAMETER, String.format(ErrorCode.REQUIRED_PARAMETER.getDefaultMessage(), "election_name"));
        }
        /*
        if (candidateName == null || candidateName.isBlank()) {
            throw new IllegalPayloadArgumentException(ErrorCode.REQUIRED_PARAMETER, String.format(ErrorCode.REQUIRED_PARAMETER.getDefaultMessage(), "candidate_name"));
        }*/
        if (votingTime == null || votingTime.isBlank()) {
            throw new IllegalPayloadArgumentException(ErrorCode.REQUIRED_PARAMETER, String.format(ErrorCode.REQUIRED_PARAMETER.getDefaultMessage(), "voting_time"));
        }

    }
}
