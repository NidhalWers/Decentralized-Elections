package model;

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
        this.hash = new StringBuilder()
                .append(previousHash)
                .append(timeStamp.toString())
                .append(data).toString();
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
