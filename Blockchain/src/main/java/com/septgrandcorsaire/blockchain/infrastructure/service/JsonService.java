package com.septgrandcorsaire.blockchain.infrastructure.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.septgrandcorsaire.blockchain.api.configuration.JacksonConfiguration;

public class JsonService {

    public static String toJson(Object object) {
        ObjectMapper objectMapper = new JacksonConfiguration().objectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
