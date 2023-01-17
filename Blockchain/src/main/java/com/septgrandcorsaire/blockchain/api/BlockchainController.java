package com.septgrandcorsaire.blockchain.api;

import com.septgrandcorsaire.blockchain.Application;
import com.septgrandcorsaire.blockchain.api.payload.ElectionPayload;
import com.septgrandcorsaire.blockchain.api.payload.VotePayload;
import com.septgrandcorsaire.blockchain.api.resource.BlockChainResource;
import com.septgrandcorsaire.blockchain.api.resource.BlockResource;
import com.septgrandcorsaire.blockchain.api.resource.ElectionResource;
import com.septgrandcorsaire.blockchain.api.resource.ElectionResultResource;
import com.septgrandcorsaire.blockchain.application.ElectionApplicationService;
import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageBlockchainCreated;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageElectionResult;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageFinishedElection;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageOngoingElection;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.code.ElectionState;
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
        return BlockChainResource.of(result.blockChain, result.apiKey, result.electionStatus);
    }

    @GetMapping(value = "/smart-vote/api/v1/get-election/{election_name}")
    public ElectionResource getElection(@PathVariable("election_name") String input, @RequestParam(required = false) String status) {
        Application.LOGGER.info(String.format("GET /smart-vote/api/v1/get-election/%s?status=%s", input, status));
        MessageElectionResult result = electionApplicationService.getElectionData(input, status);
        if (result.code().equals(ElectionState.ONGOING))
            return BlockChainResource.of(((MessageOngoingElection) result).blockChain, null, status);
        else
            return ElectionResultResource.of(((MessageFinishedElection) result).electionResult, status);

    }

    @GetMapping(value = "/smart-vote/api/v1/get-sandbox/")
    public ElectionResource getSandboxElection() {
        Application.LOGGER.info("GET /smart-vote/api/v1/get-sandbox/");
        return getElection("sandbox", null);
    }

    @GetMapping(value = "/smart-vote/api/v1/get-vote")
    public BlockResource getVoteInElection(@RequestParam String election, @RequestParam(required = false) String status, @RequestParam String vote) {
        Application.LOGGER.info(String.format("GET /smart-vote/api/v1/get-election/%s?status=%s&vote='%s'", election, status, vote));
        Block result = electionApplicationService.getVoteInElection(election, status, vote);
        return BlockResource.of(result);
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
