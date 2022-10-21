package com.septgrandcorsaire.blockchain.api;

import com.septgrandcorsaire.blockchain.api.payload.ElectionPayload;
import com.septgrandcorsaire.blockchain.api.payload.VotePayload;
import com.septgrandcorsaire.blockchain.api.resource.BlockChainResource;
import com.septgrandcorsaire.blockchain.api.resource.BlockResource;
import com.septgrandcorsaire.blockchain.application.ElectionApplicationService;
import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@RestController
public class BlockchainController {

    private ElectionApplicationService electionApplicationService;

    public BlockchainController(ElectionApplicationService electionApplicationService) {
        this.electionApplicationService = electionApplicationService;
    }

    @PostMapping(value = "/smart-vote/api/v1/create-election",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BlockChainResource createElection(@RequestBody ElectionPayload payload) {
        BlockChain result = electionApplicationService.createBlockchainForElection(payload.toQuery());
        return BlockChainResource.of(result);
    }

    @GetMapping(value = "/smart-vote/api/v1/get-election/{election_name}")
    public BlockChainResource getElection(@PathVariable("election_name") String input) {
        BlockChain result = electionApplicationService.getElectionData(input);
        return BlockChainResource.of(result);
    }

    @PostMapping(value = "/smart-vote/api/v1/vote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BlockResource voteInElection(@RequestBody VotePayload payload) {
        Block result = electionApplicationService.voteInElection(payload.toQuery());
        return BlockResource.of(result);
    }

}
