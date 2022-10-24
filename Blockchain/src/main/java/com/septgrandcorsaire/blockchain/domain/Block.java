package com.septgrandcorsaire.blockchain.domain;


import com.septgrandcorsaire.blockchain.infrastructure.service.CryptographyService;
import com.septgrandcorsaire.blockchain.infrastructure.util.Utils;

import java.time.LocalDateTime;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class Block {

    private int index;

    private String hash;

    private String previousHash;

    private LocalDateTime timeStamp;

    private Data data;

    private int nonce;

    private Block(Builder builder) {
        this.index = builder.index;
        this.previousHash = builder.previousHash;
        this.timeStamp = builder.timeStamp;
        this.data = builder.data;
        this.hash = computeHash();
        this.nonce = 0;
    }

    public int getIndex() {
        return index;
    }

    public String getHash() {
        return hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public Data getData() {
        return data;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Block #").append(index)
                .append("[previous Hash : ").append(previousHash).append(",\n")
                .append("timestamp : ").append(timeStamp.toString()).append(",\n")
                .append("data : ").append(data).append(",\n")
                .append("hash : ").append(hash).append(" ]")
                .toString();
    }

    public String computeHash() {
        return CryptographyService.computeAlgorithmSha3(new StringBuilder()
                .append(index)
                .append(previousHash)
                .append(timeStamp.toString())
                .append(data.toString())
                .append(nonce).toString());
    }

    public Block mineBlock(int difficulty) {
        nonce = 0;
        while (!getHash().substring(0, difficulty).equals(Utils.getStringOfZeros(difficulty))) {
            nonce++;
            hash = computeHash();
        }
        return this;
    }

    public boolean isValid() {
        return computeHash().equals(this.hash);
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private int index;
        private String previousHash;

        private LocalDateTime timeStamp;

        private Data data;

        public Builder index(int index) {
            this.index = index;
            return this;
        }

        public Builder previousHash(String previousHash) {
            this.previousHash = previousHash;
            return this;
        }

        public Builder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder data(Data data) {
            this.data = data;
            return this;
        }

        public Builder data(String data) {
            this.data = StringData.of(data);
            return this;
        }

        public Block build() {
            return new Block(this);
        }
    }
}
