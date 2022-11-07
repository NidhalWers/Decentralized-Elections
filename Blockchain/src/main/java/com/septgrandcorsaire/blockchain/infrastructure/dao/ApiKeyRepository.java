package com.septgrandcorsaire.blockchain.infrastructure.dao;

import com.sun.jdi.InternalException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class ApiKeyRepository {

    public static ApiKeyRepository INSTANCE = new ApiKeyRepository();

    private Map<String, String> apiKeyRepository = new HashMap<>();

    private ApiKeyRepository() {
        this.apiKeyRepository = apiKeyRepository;
    }

    public void addKey(String electionName, String inputKey) {
        if (apiKeyRepository.containsKey(electionName))
            throw new InternalException(String.format("An api key already exists for '%s'", electionName));
        apiKeyRepository.put(electionName, inputKey);
    }

    public boolean isApiKeyCorrespondingToElection(String inputKey, String electionName) {
        return apiKeyRepository.get(electionName).equals(inputKey);
    }

    public void clearRepository() {
        apiKeyRepository.clear();
    }
}
