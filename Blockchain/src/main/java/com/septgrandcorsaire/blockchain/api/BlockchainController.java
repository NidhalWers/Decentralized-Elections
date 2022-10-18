package com.septgrandcorsaire.blockchain.api;

import com.septgrandcorsaire.blockchain.api.payload.ElectionPayload;
import com.septgrandcorsaire.blockchain.application.ElectionApplicationService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
    public String createElection(@RequestBody ElectionPayload payload) {
        return electionApplicationService.createBlockchainForElection(payload.toQuery())
                .toString();
    }

}
