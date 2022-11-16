package com.septgrandcorsaire.blockchain.api;

import com.septgrandcorsaire.blockchain.Application;
import com.septgrandcorsaire.blockchain.api.error.exception.ElectionAlreadyFinishedException;
import com.septgrandcorsaire.blockchain.api.payload.ElectionPayload;
import com.septgrandcorsaire.blockchain.api.payload.VotePayload;
import com.septgrandcorsaire.blockchain.api.resource.BlockChainResource;
import com.septgrandcorsaire.blockchain.api.resource.BlockResource;
import com.septgrandcorsaire.blockchain.api.resource.ElectionResource;
import com.septgrandcorsaire.blockchain.api.resource.ElectionResultResource;
import com.septgrandcorsaire.blockchain.application.ElectionApplicationService;
import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageBlockchainCreated;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@CrossOrigin
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
        Application.LOGGER.info("GET /smart-vote/api/v1/create-election");
        MessageBlockchainCreated result = electionApplicationService.createBlockchainForElection(payload.toQuery());
        return BlockChainResource.of(result.blockChain, result.apiKey);
    }

    @GetMapping(value = "/smart-vote/api/v1/get-election/{election_name}")
    public ElectionResource getElection(@PathVariable("election_name") String input) {
        Application.LOGGER.info("GET /smart-vote/api/v1/get-election/" + input);
        BlockChain result;
        try {
            result = electionApplicationService.getElectionData(input);
        } catch (ElectionAlreadyFinishedException exception) {
            return ElectionResultResource.of(exception.getElectionResult());
        }
        return BlockChainResource.of(result, null);
    }

    @GetMapping(value = "/smart-vote/api/v1/get-sandbox/")
    public ElectionResource getSandboxElection() {
        Application.LOGGER.info("GET /smart-vote/api/v1/get-sandbox/");
        return getElection("sandbox");
    }

    @PostMapping(value = "/smart-vote/api/v1/vote",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public BlockResource voteInElection(@RequestBody VotePayload payload) {
        Application.LOGGER.info("POST /smart-vote/api/v1/vote");
        Block result = electionApplicationService.voteInElection(payload.toQuery());
        return BlockResource.of(result);
    }

}
