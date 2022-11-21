package com.septgrandcorsaire.blockchain.infrastructure.adapter;

import com.septgrandcorsaire.blockchain.api.error.exception.ElectionAlreadyFinishedException;
import com.septgrandcorsaire.blockchain.api.error.exception.ElectionNotFoundException;
import com.septgrandcorsaire.blockchain.api.error.exception.ElectionNotStartedException;
import com.septgrandcorsaire.blockchain.api.error.exception.VoterHasAlreadyVotedException;
import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.application.VoteQuery;
import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.domain.ElectionInitializationData;
import com.septgrandcorsaire.blockchain.domain.VotingData;
import com.septgrandcorsaire.blockchain.infrastructure.dao.BlockchainRepository;
import com.septgrandcorsaire.blockchain.infrastructure.model.message.MessageOngoingElection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ElectionDomainServiceTest {

    private final ElectionDomainService domainService = new ElectionDomainService();
    private static final LocalDateTime endingTestTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);

    @BeforeEach
    void init() {
        BlockChain blockchain1 = new BlockChain("test", 4);
        final ElectionQuery query = ElectionQuery.builder()
                .candidates(List.of("one", "two"))
                .startingDate(LocalDateTime.of(2022, 10, 24, 8, 30, 00))
                .closingDate(endingTestTime)
                .electionName("test")
                .build();
        blockchain1.addBlock(blockchain1.newBlock(ElectionInitializationData.fromElectionQuery(query), 0, null));
        BlockchainRepository.INSTANCE.addBlockchain("test", blockchain1);
    }

    @AfterEach
    void tearDown() {
        domainService.deleteAllElections();
    }

    /********************************************************************************************
     *
     *              Test of the get blockchain service
     *
     *********************************************************************************************/

    @Test
    void testGetBlockchainForElection() {
        final String query = "test";

        final BlockChain actualBlockchain = ((MessageOngoingElection) domainService.getBlockchainForElection(query)).blockChain;

        assertThat(actualBlockchain).isNotNull();
        assertThat(actualBlockchain.getName()).isEqualTo("test");

    }


    /********************************************************************************************
     *
     *              Test of the create blockchain service
     *
     *********************************************************************************************/
    @Test
    void testCreateBlockchainForElection() {
        final ElectionQuery query = ElectionQuery.builder()
                .candidates(List.of("one", "two"))
                .startingDate(LocalDateTime.of(2022, 10, 24, 8, 30, 0))
                .closingDate(LocalDateTime.of(2022, 10, 25, 10, 0, 0))
                .electionName("first_test")
                .build();

        final BlockChain blockChain = domainService.createBlockchainForElection(query).blockChain;

        assertThat(blockChain).isNotNull();
        assertThat(blockChain.getName()).isEqualTo("first_test");
        assertThat(blockChain.getInitializationData().getCandidates()).containsExactly("one", "two");
        assertThat(blockChain.getInitializationData().getStartingDate().getYear()).isEqualTo(2022);
        assertThat(blockChain.getInitializationData().getStartingDate().getMonthValue()).isEqualTo(10);
        assertThat(blockChain.getInitializationData().getStartingDate().getDayOfMonth()).isEqualTo(24);
        assertThat(blockChain.getInitializationData().getStartingDate().getHour()).isEqualTo(8);
        assertThat(blockChain.getInitializationData().getStartingDate().getMinute()).isEqualTo(30);
        assertThat(blockChain.getInitializationData().getClosingDate().getYear()).isEqualTo(2022);
        assertThat(blockChain.getInitializationData().getClosingDate().getMonthValue()).isEqualTo(10);
        assertThat(blockChain.getInitializationData().getClosingDate().getDayOfMonth()).isEqualTo(25);
        assertThat(blockChain.getInitializationData().getClosingDate().getHour()).isEqualTo(10);
        assertThat(blockChain.getInitializationData().getClosingDate().getMinute()).isEqualTo(0);
    }


    /********************************************************************************************
     *
     *              Test of the vote service
     *
     *********************************************************************************************/

    @Test
    void testVoteInElection() {
        VoteQuery query = VoteQuery.builder()
                .electionName("test")
                .candidateName("one")
                .votingDate(LocalDateTime.of(2022, 10, 24, 12, 0, 0))
                .voterId("voter1")
                .build();

        Block actualResult = domainService.voteInElection(query);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getData()).isInstanceOf(VotingData.class);
        VotingData data = (VotingData) actualResult.getData();
        assertThat(data.getElectionName()).isEqualTo("test");
        assertThat(data.getCandidateName()).isEqualTo("one");
        assertThat(data.getVotingDate()).isAfterOrEqualTo(LocalDateTime.of(2022, 10, 24, 12, 0, 0));
    }

    @Test
    void testVoteInElectionNotExistingElection() {
        VoteQuery query = VoteQuery.builder()
                .electionName("FalseElection")
                .candidateName("one")
                .votingDate(LocalDateTime.of(2022, 10, 24, 12, 0, 0))
                .voterId("voter1")
                .build();

        ElectionNotFoundException exception = assertThrows(ElectionNotFoundException.class, () -> {
            domainService.voteInElection(query);
        });

        assertThat(exception.getMessage()).isEqualTo("Election 'FalseElection' does not exist");
    }

    @Test
    void testVoteInElectionBeforeBeginning() {
        VoteQuery query = VoteQuery.builder()
                .electionName("test")
                .candidateName("one")
                .votingDate(LocalDateTime.of(2022, 10, 23, 12, 0, 0))
                .voterId("voter1")
                .build();

        ElectionNotStartedException exception = assertThrows(ElectionNotStartedException.class, () -> {
            domainService.voteInElection(query);
        });

        assertThat(exception.getMessage()).isEqualTo("Election 'test' has not started yet, wait until 2022-10-24T08:30");
    }

    @Test
    void testVoteInElectionAfterEnding() {
        VoteQuery query = VoteQuery.builder()
                .electionName("test")
                .candidateName("one")
                .votingDate(endingTestTime.plusDays(1).plusHours(5))
                .voterId("voter1")
                .build();

        ElectionAlreadyFinishedException exception = assertThrows(ElectionAlreadyFinishedException.class, () -> {
            domainService.voteInElection(query);
        });

        assertThat(exception.getMessage()).isEqualTo("Election 'test' is already finished since " + endingTestTime.toString());
    }

    @Test
    void testVoteInElectionInvalidCandidate() {
        VoteQuery query = VoteQuery.builder()
                .electionName("test")
                .candidateName("NOT_EXISTING")
                .votingDate(LocalDateTime.now())
                .voterId("voter1")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            domainService.voteInElection(query);
        });

        assertThat(exception.getMessage()).isEqualTo("the name 'NOT_EXISTING' is not part of the test's candidates");
    }

    @Test
    void testVoteInElectionHasAlreadyVoted() {
        VoteQuery query = VoteQuery.builder()
                .electionName("test")
                .candidateName("one")
                .votingDate(LocalDateTime.of(2022, 10, 24, 12, 0, 0))
                .voterId("voter1")
                .build();

        Block actualResult = domainService.voteInElection(query);

        VoterHasAlreadyVotedException exception = assertThrows(VoterHasAlreadyVotedException.class, () -> {
            domainService.voteInElection(query);
        });

        /*
        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getData()).isInstanceOf(VotingData.class);
        VotingData data = (VotingData) actualResult.getData();
        assertThat(data.getElectionName()).isEqualTo("test");
        assertThat(data.getCandidateName()).isEqualTo("one");
        assertThat(data.getVotingDate()).isAfterOrEqualTo(LocalDateTime.of(2022, 10, 24, 12, 0, 0));
        */

        assertThat(exception.getMessage()).isEqualTo("voter1 has already voted for the election named 'test'");
    }
}