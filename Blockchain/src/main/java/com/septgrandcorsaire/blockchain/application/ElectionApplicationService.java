package com.septgrandcorsaire.blockchain.application;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.adapter.ElectionDomainService;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageBlockchainCreated;
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

    public BlockChain getElectionData(String inputRequest) {
        if (inputRequest.isBlank()) {
            throw new IllegalArgumentException("must provide a valid election name");
        }
        return electionDomainService.getBlockchainForElection(inputRequest);
    }

    public Block voteInElection(VoteQuery query) {
        return electionDomainService.voteInElection(query);
    }
}
