package com.septgrandcorsaire.blockchain;

import com.septgrandcorsaire.blockchain.application.VoteQuery;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.domain.ElectionInitializationData;
import com.septgrandcorsaire.blockchain.infrastructure.adapter.ElectionDomainService;
import com.septgrandcorsaire.blockchain.infrastructure.dao.ApiKeyRepository;
import com.septgrandcorsaire.blockchain.infrastructure.dao.BlockchainRepository;
import com.septgrandcorsaire.blockchain.infrastructure.util.DataGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.List;


/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
@SpringBootApplication
public class Application {

    public static Logger LOGGER = LoggerFactory.getLogger(Application.class);

    private static ElectionDomainService electionDomainService;


    @Value("${mining.difficulty}")
    public int MINING_DIFFICULTY;

    @Value("${sandbox.api.key}")
    public String sandboxApiKey;


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //createSandboxElection();
    }

    @PostConstruct
    private void createSandboxElection() {
        LOGGER.info("Creation of the Sandbox Election");
        long startTimeOfTheMethod = System.nanoTime();
        electionDomainService = new ElectionDomainService();
        BlockChain sandboxBlockchain = new BlockChain("sandbox", MINING_DIFFICULTY);

        //create election and store it
        final int range = 4;
        final int min = 2;

        final LocalDateTime sandboxElectionStartingDate = LocalDateTime.of(2022, 11, 1, 10, 0);
        final LocalDateTime sandboxElectionEndingDate = LocalDateTime.of(2022, 11, 1, 21, 0);

        sandboxBlockchain.addBlock(sandboxBlockchain.newBlock(
                new ElectionInitializationData(
                        List.of("William", "Mormox", "Nidhal", "Fares", "Wassim", "Chamss", "Karim")
                                .subList(0, (int) (Math.random() * range) + min),
                        sandboxElectionStartingDate,
                        sandboxElectionEndingDate,
                        sandboxBlockchain.getName()
                ),
                0,
                null));

        BlockchainRepository.INSTANCE.addBlockchain(sandboxBlockchain.getName(), sandboxBlockchain);
        ApiKeyRepository.INSTANCE.addKey(sandboxBlockchain.getName(), sandboxApiKey);

        //generate 50 votes
        for (int i = 0; i < 50; i++) {
            electionDomainService.voteInElection(VoteQuery.builder()
                    .electionName(sandboxBlockchain.getName())
                    .candidateName(DataGenerator.chooseAValueRandomlyInAList(sandboxBlockchain.getInitializationData().getCandidates()))
                    .votingDate(DataGenerator.newDateTimeBetween(sandboxElectionStartingDate, sandboxElectionEndingDate))
                    .voterId("voter_" + i)
                    .build());
        }
        LOGGER.info("Sandbox election successfully created in " + ((System.nanoTime() - startTimeOfTheMethod) / 1000000) + " ms");//todo compute time
    }

}
