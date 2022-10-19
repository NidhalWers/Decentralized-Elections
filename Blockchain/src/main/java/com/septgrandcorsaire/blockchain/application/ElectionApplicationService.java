package com.septgrandcorsaire.blockchain.application;

import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.ElectionDomainService;
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

    public BlockChain createBlockchainForElection(final ElectionQuery query) {
        if (query.getCandidates().isEmpty()) {
            throw new IllegalArgumentException("must provide a not empty list of candidates");
        }
        if (query.getElectionName().isBlank()) {
            throw new IllegalArgumentException("must provide a valid election name");
        }

        return electionDomainService.createBlockchainForElection(query);
    }

}
