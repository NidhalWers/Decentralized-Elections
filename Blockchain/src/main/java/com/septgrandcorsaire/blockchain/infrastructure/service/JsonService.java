package com.septgrandcorsaire.blockchain.infrastructure.service;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.septgrandcorsaire.blockchain.api.configuration.JacksonConfiguration;
import com.septgrandcorsaire.blockchain.api.payload.VotePayload;

public class JsonService {

    public static String toJson(Object object) {
        ObjectMapper objectMapper = new JacksonConfiguration().objectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static VotePayload votePayloadfromJson(String jsonBody) {
        ObjectMapper objectMapper = new JacksonConfiguration().objectMapper();
        try {
            return objectMapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL)
                    .enable(JsonGenerator.Feature.IGNORE_UNKNOWN)
                    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    .readValue(jsonBody, VotePayload.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
