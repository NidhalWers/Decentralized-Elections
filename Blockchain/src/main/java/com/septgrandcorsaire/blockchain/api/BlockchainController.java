package com.septgrandcorsaire.blockchain.api;

import com.septgrandcorsaire.blockchain.api.payload.ElectionPayload;
import com.septgrandcorsaire.blockchain.api.resource.BlockChainResource;
import com.septgrandcorsaire.blockchain.application.ElectionApplicationService;
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
        return BlockChainResource.of(result.getName(), result.getBlocks());
    }

    @GetMapping(value = "/smart-vote/api/v1/get-election/{election_name}")
    public BlockChainResource getElection(@PathVariable("election_name") String electionName) {
        BlockChain result = electionApplicationService.getElectionData(electionName);
        return BlockChainResource.of(result.getName(), result.getBlocks());
    }

}
