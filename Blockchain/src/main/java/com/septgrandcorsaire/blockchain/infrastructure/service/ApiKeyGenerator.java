package com.septgrandcorsaire.blockchain.infrastructure.service;

import se.emirbuc.randomsentence.RandomSentences;

public class ApiKeyGenerator {

    public static String generateKey() {
        String sentence = RandomSentences.generateRandomSentence(RandomSentences.Length.MEDIUM);
        return CryptographyService.computeAlgorithmSha3(sentence);
    }
}
