package com.septgrandcorsaire.blockchain.application;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.infrastructure.adapter.ElectionDomainService;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageBlockchainCreated;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageElectionResult;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageVoteInElection;
import org.springframework.stereotype.Service;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@Service
public class ElectionApplicationService {

    private final ElectionDomainService electionDomainService;

    public ElectionApplicationService(final ElectionDomainService electionDomainService) {
        this.electionDomainService = electionDomainService;
    }

    public MessageBlockchainCreated createBlockchainForElection(final ElectionQuery query) {
        if (query.getCandidates().isEmpty()) {
            throw new IllegalArgumentException("must provide a not empty list of candidates");
        }
        if (query.getElectionName().isBlank()) {
            throw new IllegalArgumentException("must provide a valid election name");
        }

        return electionDomainService.createBlockchainForElection(query);
    }

    public MessageElectionResult getElectionData(String electionRequested, String statusQueried) {
        if (electionRequested == null || electionRequested.isBlank()) {
            throw new IllegalArgumentException("must provide a valid election name");
        }
        return electionDomainService.getBlockchainForElection(electionRequested, statusQueried);
    }

    public MessageElectionResult getElectionData(String electionRequested) {
        return getElectionData(electionRequested, null);
    }

    public Block voteInElection(VoteQuery query) {
        return electionDomainService.voteInElection(query);
    }

    public MessageVoteInElection getVoteInElection(String election, String status, String vote) {
        if (election == null || election.isBlank())
            throw new IllegalArgumentException("must provide a valid election name");
        return electionDomainService.getVoteInElection(election, status, vote);
    }
}
