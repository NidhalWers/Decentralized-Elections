package model;

import service.Cryptography;

import java.time.LocalDateTime;

public class Block {

    private String hash;

    private String previousHash;

    private LocalDateTime timeStamp;

    private String data;

    private Block(Builder builder) {
        this.previousHash = builder.previousHash;
        this.timeStamp = builder.timeStamp;
        this.data = builder.data;
        this.hash = computeHash();
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

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("Block #").append(hash)
                .append("[previous Hash : ").append(previousHash).append(",\n")
                .append("timestamp : ").append(timeStamp.toString()).append(",\n")
                .append("data : ").append(data).append(" ]")
                .toString();
    }

    public String computeHash() {
        return Cryptography.computeAlgorithmSha3(new StringBuilder()
                .append(previousHash)
                .append(timeStamp.toString())
                .append(data).toString());
    }

    public boolean isValid(){
        return computeHash().equals(this.hash);
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private String previousHash;

        private LocalDateTime timeStamp;

        private String data;

        public Builder previousHash(String previousHash) {
            this.previousHash = previousHash;
            return this;
        }

        public Builder timeStamp(LocalDateTime timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public Builder data(String data) {
            this.data = data;
            return this;
        }

        public Block build(){
            return new Block(this);
        }
    }
}
