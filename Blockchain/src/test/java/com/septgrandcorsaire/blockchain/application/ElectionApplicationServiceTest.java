package com.septgrandcorsaire.blockchain.application;

import com.septgrandcorsaire.blockchain.domain.Block;
import com.septgrandcorsaire.blockchain.domain.BlockChain;
import com.septgrandcorsaire.blockchain.domain.ElectionInitializationData;
import com.septgrandcorsaire.blockchain.domain.VotingData;
import com.septgrandcorsaire.blockchain.infrastructure.adapter.ElectionDomainService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

class ElectionApplicationServiceTest {

    private final ElectionApplicationService applicationService;

    private final ElectionDomainService mockDomainService;

    public ElectionApplicationServiceTest() {
        this.mockDomainService = Mockito.mock(ElectionDomainService.class);
        this.applicationService = new ElectionApplicationService(mockDomainService);
    }

    @Test
    void testCreateBlockchainForElection() {
        final ElectionQuery query = ElectionQuery.builder()
                .candidates(List.of("one", "two"))
                .startingDate(LocalDateTime.of(2022, 10, 24, 8, 30, 00))
                .closingDate(LocalDateTime.of(2022, 10, 25, 10, 0, 00))
                .electionName("first_test")
                .build();

        final BlockChain blockChainForMock = new BlockChain(query.getElectionName(), 4);
        blockChainForMock.addBlock(blockChainForMock.newBlock(ElectionInitializationData.fromElectionQuery(query), 0, null));


        when(mockDomainService.createBlockchainForElection(Mockito.argThat(request ->
                request.getElectionName().equals(query.getElectionName())
                        && request.getCandidates().get(0).equals("one")
                        && request.getCandidates().get(1).equals("two")
                        && request.getStartingDate().getYear() == 2022
                        && request.getStartingDate().getMonthValue() == 10
                        && request.getStartingDate().getDayOfMonth() == 24
                        && request.getStartingDate().getHour() == 8
                        && request.getStartingDate().getMinute() == 30
                        && request.getElectionName().equals("first_test")
                        && request.getClosingDate().getYear() == 2022
                        && request.getClosingDate().getMonthValue() == 10
                        && request.getClosingDate().getDayOfMonth() == 25
                        && request.getClosingDate().getHour() == 10
                        && request.getClosingDate().getMinute() == 0
        ))).thenReturn(blockChainForMock);

        final BlockChain actualResult = applicationService.createBlockchainForElection(query);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getName()).isEqualTo("first_test");
        assertThat(actualResult.getBlocks()).hasSize(1);
        assertThat(actualResult.getInitializationData().getCandidates()).containsExactly("one", "two");
        assertThat(actualResult.getInitializationData().getStartingDate()).isAfterOrEqualTo(query.getStartingDate());
        assertThat(actualResult.getInitializationData().getClosingDate()).isAfterOrEqualTo(query.getClosingDate());
    }

    @Test
    void testCreateBlockchainForElectionEmptyCandidatesList() {
        final ElectionQuery query = ElectionQuery.builder()
                .candidates(List.of())
                .startingDate(LocalDateTime.of(2022, 10, 24, 8, 30, 00))
                .closingDate(LocalDateTime.of(2022, 10, 25, 10, 0, 00))
                .electionName("first_test")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            applicationService.createBlockchainForElection(query);
        });

        assertThat(exception.getMessage()).isEqualTo("must provide a not empty list of candidates");
    }

    @Test
    void testCreateBlockchainForElectionBlankElectionName() {
        final ElectionQuery query = ElectionQuery.builder()
                .candidates(List.of("one", "two"))
                .startingDate(LocalDateTime.of(2022, 10, 24, 8, 30, 00))
                .closingDate(LocalDateTime.of(2022, 10, 25, 10, 0, 00))
                .electionName("")
                .build();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            applicationService.createBlockchainForElection(query);
        });

        assertThat(exception.getMessage()).isEqualTo("must provide a valid election name");
    }

    @Test
    void testGetElectionData() {
        String inputRequest = "first_test";

        final ElectionQuery query = ElectionQuery.builder()
                .candidates(List.of("one", "two"))
                .startingDate(LocalDateTime.of(2022, 10, 24, 8, 30, 00))
                .closingDate(LocalDateTime.of(2022, 10, 25, 10, 0, 00))
                .electionName("first_test")
                .build();

        final BlockChain blockChainForMock = new BlockChain("first_test", 4);
        blockChainForMock.addBlock(blockChainForMock.newBlock(ElectionInitializationData.fromElectionQuery(query), 0, null));

        when(mockDomainService.getBlockchainForElection(Mockito.argThat(request ->
                request.equals(inputRequest)
        ))).thenReturn(blockChainForMock);

        final BlockChain actualResult = applicationService.getElectionData(inputRequest);

        assertThat(actualResult).isNotNull();
        assertThat(actualResult.getName()).isEqualTo("first_test");
        assertThat(actualResult.getBlocks()).hasSize(1);
        assertThat(actualResult.getInitializationData().getCandidates()).containsExactly("one", "two");
        assertThat(actualResult.getInitializationData().getStartingDate()).isAfterOrEqualTo(query.getStartingDate());
        assertThat(actualResult.getInitializationData().getClosingDate()).isAfterOrEqualTo(query.getClosingDate());
    }

    @Test
    void testGetElectionDataBlankInput() {
        String inputRequest = "";

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            applicationService.getElectionData(inputRequest);
        });

        assertThat(exception.getMessage()).isEqualTo("must provide a valid election name");
    }


    @Test
    void testVoteInElection() {
        final VoteQuery query = VoteQuery.builder()
                .electionName("first_test")
                .candidateName("one")
                .votingDate(LocalDateTime.of(2022, 10, 24, 12, 30))
                .build();

        final Block blockForMockResponse = Block.builder()
                .index(1)
                .previousHash("none")
                .timeStamp(LocalDateTime.of(2022, 10, 24, 12, 30))
                .data(VotingData.fromVoteQuery(query))
                .build();

        when(applicationService.voteInElection(Mockito.argThat(request ->
                request.getElectionName().equals("first_test")
                        && request.getCandidateName().equals("one")
                        && request.getVotingDate().getYear() == 2022
                        && request.getVotingDate().getMonthValue() == 10
                        && request.getVotingDate().getDayOfMonth() == 24
                        && request.getVotingDate().getHour() == 12
                        && request.getVotingDate().getMinute() == 30
        ))).thenReturn(blockForMockResponse);

        final Block actualBlockResponse = applicationService.voteInElection(query);

        assertThat(actualBlockResponse).isNotNull();
        assertThat(actualBlockResponse.getData()).isInstanceOf(VotingData.class);
        VotingData actualData = (VotingData) actualBlockResponse.getData();
        assertThat(actualData.getElectionName()).isEqualTo("first_test");
        assertThat(actualData.getCandidateName()).isEqualTo("one");
        assertThat(actualData.getVotingDate().getYear()).isEqualTo(2022);
        assertThat(actualData.getVotingDate().getMonthValue()).isEqualTo(10);
        assertThat(actualData.getVotingDate().getDayOfMonth()).isEqualTo(24);
        assertThat(actualData.getVotingDate().getHour()).isEqualTo(12);
        assertThat(actualData.getVotingDate().getMinute()).isEqualTo(30);


    }
}