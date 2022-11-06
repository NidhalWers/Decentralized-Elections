package com.septgrandcorsaire.blockchain.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.septgrandcorsaire.blockchain.Application;
import com.septgrandcorsaire.blockchain.api.error.exception.ErrorCode;
import com.septgrandcorsaire.blockchain.api.payload.ElectionPayload;
import com.septgrandcorsaire.blockchain.api.payload.VotePayload;
import com.septgrandcorsaire.blockchain.application.ElectionQuery;
import com.septgrandcorsaire.blockchain.infrastructure.adapter.ElectionDomainService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class BlockchainControllerIT {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @Autowired
    private ElectionDomainService domainService;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        ElectionQuery queryFirstElection = ElectionQuery.builder()
                .electionName("first_election")
                .startingDate(startingTestDateTime)
                .closingDate(endingTestDateTime)
                .candidates(List.of("Henry", "Julius", "Mohammed"))
                .build();

        domainService.createBlockchainForElection(queryFirstElection);
    }

    @AfterEach
    public void tearDown() {
        domainService.deleteAllElections();
    }

    static final String CREATE_ELECTION_ENDPOINT = "/smart-vote/api/v1/create-election";

    static final String RESULT_ENDPOINT = "/smart-vote/api/v1/get-election/{election_name}";

    static final String VOTE_ENDPOINT = "/smart-vote/api/v1/vote";

    private static final LocalDateTime endingTestDateTime = LocalDateTime.now().plusDays(1).withHour(10).withMinute(0).withSecond(0).withNano(0);

    private static final LocalDateTime startingTestDateTime = LocalDateTime.of(2022, 10, 24, 10, 0, 0);


    /********************************************************************************************
     *
     *              Test of the create election endpoint
     *
     *********************************************************************************************/


    @Test
    @DisplayName("Given valid payload," +
            "When creating election," +
            "Then return the a complete blockchain resource")
    void testCreateElection() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.election_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockchain_valid").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].hash").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates.size()").value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.starting_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.closing_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.election_name").isString())
        ;
    }

    @Test
    @DisplayName("Given valid payload with 'false' blank vote count," +
            "When creating election," +
            "Then return the a complete blockchain resource")
    void testCreateElectionWithoutBlankVote() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .countBlankVotes("false")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.election_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockchain_valid").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].hash").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.starting_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.closing_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.election_name").isString())
        ;
    }

    @Test
    @DisplayName("Given valid payload with 'FALSE' blank vote count," +
            "When creating election," +
            "Then return the a complete blockchain resource")
    void testCreateElectionWithoutBlankVoteUpperCaseParameter() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .countBlankVotes("FALSE")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.election_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockchain_valid").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].hash").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates", hasSize(3)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.starting_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.closing_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.election_name").isString())
        ;
    }

    @Test
    @DisplayName("Given valid payload with 'true' blank vote count," +
            "When creating election," +
            "Then return the a complete blockchain resource")
    void testCreateElectionWithBlankVote() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .countBlankVotes("true")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.election_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockchain_valid").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].hash").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates", hasItem("blank_votes")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.starting_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.closing_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.election_name").isString())
        ;
    }

    @Test
    @DisplayName("Given valid payload with 'TRUE' blank vote count," +
            "When creating election," +
            "Then return the a complete blockchain resource")
    void testCreateElectionWithBlankVoteUpperCase() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .countBlankVotes("TRUE")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.election_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockchain_valid").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].hash").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates", hasSize(4)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.candidates", hasItem("blank_votes")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.starting_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.closing_date").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks[0].data.election_name").isString())
        ;
    }

    @Test
    @DisplayName("Given payload with invalid blank vote count," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionWithInvalidBlankVoteParameter() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .countBlankVotes("toto")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.INVALID_BOOLEAN_FORMAT.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'count_blank_votes' is not in a boolean format : should be true or false"))
        ;
    }


    @Test
    @DisplayName("Given payload without election name," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionWithoutElectionName() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.REQUIRED_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'election_name' is required."))
        ;
    }

    @Test
    @DisplayName("Given payload without starting date," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionWithoutStartingDate() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.REQUIRED_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'starting_date' is required."))
        ;
    }

    @Test
    @DisplayName("Given payload with a bad formatted starting date," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionWithBadFormattedStartingDate() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022/10/24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.BAD_DATE_FORMAT.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The 'starting_date' parameter should be in [YY-MM-DD'T'HH:mm::ss] format"))
        ;
    }

    @Test
    @DisplayName("Given payload without ending date," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionWithoutClosingDate() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.REQUIRED_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'closing_date' is required."))
        ;
    }

    @Test
    @DisplayName("Given payload with a bad formatted ending date," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionWithBadFormattedClosingDate() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022/10/24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.BAD_DATE_FORMAT.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The 'closing_date' parameter should be in [YY-MM-DD'T'HH:mm::ss] format"))
        ;
    }

    @Test
    @DisplayName("Given payload with an empty candidates list," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionNoCandidates() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("french_2024_president")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of())
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);
        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.REQUIRED_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'candidates' is required."))
        ;

    }

    @Test
    @DisplayName("Given payload with of an already existing election," +
            "When creating election," +
            "Then throw exception")
    void testCreateElectionAlreadyExistent() throws Exception {
        ElectionPayload payload = ElectionPayload.builder()
                .electionName("first_election")
                .startingDate("2022-10-24T10:00")
                .closingDate("2022-10-24T21:00")
                .candidates(List.of("Othman", "Ali", "Talha"))
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(CREATE_ELECTION_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the election 'first_election' already exists"))
        ;
    }

    /********************************************************************************************
     *
     *              Test of the get election endpoint
     *
     *********************************************************************************************/

    @Test
    @DisplayName("Given the name of an election not finished yet," +
            "When getting results," +
            "Then return the blockchain resource")
    void testGetElection() throws Exception {
        String payload = "first_election";

        mockMvc.perform(get(RESULT_ENDPOINT, payload))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.election_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blockchain_valid").isBoolean())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.blocks").isNotEmpty());
    }

    @Test
    @DisplayName("Given the name of a finished election," +
            "When getting results," +
            "Then return the election results")
    void testGetFinishedElection() {//todo

    }

    @Test
    @DisplayName("Given a blanl election name," +
            "When getting election results," +
            "Then throw an exception")
    void testGetElectionNoName() throws Exception {
        String payload = "";

        mockMvc.perform(get(RESULT_ENDPOINT, payload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
        ;
    }

    @Test
    @DisplayName("Given a name of an election not created," +
            "When getting election results," +
            "Then throw an exception")
    void testGetElectionNotFound() throws Exception {
        String payload = "second_election";

        mockMvc.perform(get(RESULT_ENDPOINT, payload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.NOT_FOUND_ELECTION.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Election 'second_election' does not exist"))
        ;
    }


    /********************************************************************************************
     *
     *              Test of the vote endpoint
     *
     *********************************************************************************************/

    @Test
    @DisplayName("Given a complete payload," +
            "When voting in election," +
            "Then return the created Block resource")
    void testVoteInElection() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("first_election")
                .candidateName("Julius")
                .votingTime("2022-10-24T17:00")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.hash").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.previous_hash").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data").hasJsonPath())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.election_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.candidate_name").isString())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.voting_date").hasJsonPath())
        ;
    }

    @Test
    @DisplayName("Given a payload without the election name," +
            "When voting in election," +
            "Then throw an exception")
    void testVoteNoElectionName() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("")
                .candidateName("Julius")
                .votingTime("2022-10-24T17:00")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.REQUIRED_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'election_name' is required."))
        ;
    }

    @Test
    @DisplayName("Given a payload without the candidate name," +
            "When voting in election," +
            "Then throw an exception")
    void testVoteNoCandidateName() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("first_election")
                .candidateName("")
                .votingTime("2022-10-24T17:00")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.REQUIRED_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'candidate_name' is required."))
        ;
    }

    @Test
    @DisplayName("Given a payload without the voting time," +
            "When voting in election," +
            "Then throw an exception")
    void testVoteNoVotingTime() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("first_election")
                .candidateName("Julius")
                .votingTime("")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.REQUIRED_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Parameter 'voting_time' is required."))
        ;
    }

    @Test
    @DisplayName("Given a payload with a bad formatted voting time," +
            "When voting in election," +
            "Then throw an exception")
    void testVoteBadFormattedVotingTime() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("first_election")
                .candidateName("Julius")
                .votingTime("2022/10/24T17:00")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.BAD_DATE_FORMAT.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("The 'voting_time' parameter should be in [YY-MM-DD'T'HH:mm::ss] format"))
        ;
    }


    @Test
    @DisplayName("Given a payload about an nonexistent election," +
            "When voting in election," +
            "Then throw an exception")
    void testVoteInElectionNotExisting() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("second_election")
                .candidateName("Julius")
                .votingTime("2022-10-24T17:00")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.NOT_FOUND_ELECTION.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Election 'second_election' does not exist"))
        ;
    }

    @Test
    @DisplayName("Given a payload of a vote at the date of 2022-10-22," +
            "When voting in election which begin in 2022-10-24," +
            "Then throw an exception")
    void testVoteInElectionTooSoon() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("first_election")
                .candidateName("Julius")
                .votingTime(startingTestDateTime.minusHours(5).toString())
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.ELECTION_NOT_STARTED.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Election 'first_election' has not started yet, wait until " + startingTestDateTime.toString()))
        ;
    }

    @Test
    @DisplayName("Given a payload of a vote at the date of 2022-10-27," +
            "When voting in election which begin in 2022-10-25," +
            "Then throw an exception")
    void testVoteInElectionTooLate() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("first_election")
                .candidateName("Julius")
                .votingTime(endingTestDateTime.plusHours(7).toString())
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.ELECTION_ALREADY_FINISHED.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Election 'first_election' is already finished since " + endingTestDateTime.toString()))
        ;
    }

    @Test
    @DisplayName("Given a payload of a vote for a nonexistent candidate," +
            "When voting in election," +
            "Then throw an exception")
    void testVoteInElectionNoSuchCandidateInElection() throws Exception {
        VotePayload payload = VotePayload.builder()
                .electionName("first_election")
                .candidateName("Farouq")
                .votingTime("2022-10-24T17:00")
                .build();

        ObjectMapper mapper = new ObjectMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        String jsonPayload = mapper.writeValueAsString(payload);

        mockMvc.perform(post(VOTE_ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(jsonPayload))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value(ErrorCode.INVALID_PARAMETER.getValue()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("the name 'Farouq' is not part of the first_election's candidates"))
        ;
    }
}
