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

    private String voterId;

    private String electionStatus;

    public VoteQuery toQuery() {
        validatePayload();
        return VoteQuery.builder()
                .electionName(electionName)
                .candidateName(candidateName)
                .votingDate(parseVotingDate())
                .voterId(voterId)
                .electionStatus(electionStatus)
                .build();
    }

    private LocalDateTime parseVotingDate() {
        return parseDateTime(this.votingTime, "voting_time");
    }

    private void validatePayload() {
        if (electionName == null || electionName.isBlank()) {
            throw IllegalPayloadArgumentException.ofErrorCode(ErrorCode.REQUIRED_PARAMETER, "election_name");
        }

        if (voterId == null || voterId.isBlank()) {
            throw IllegalPayloadArgumentException.ofErrorCode(ErrorCode.REQUIRED_PARAMETER, "voter_id");
        }
        if (votingTime == null || votingTime.isBlank()) {
            throw IllegalPayloadArgumentException.ofErrorCode(ErrorCode.REQUIRED_PARAMETER, "voting_time");
        }

    }
}
