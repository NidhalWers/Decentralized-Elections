package com.septgrandcorsaire.blockchain.api.error.exception;

public enum ErrorCode {
    INVALID_PARAMETER("INVALID_PARAMETER", "Parameter '%s' is invalid."),

    REQUIRED_PARAMETER("REQUIRED_PARAMETER", "Parameter '%s' is required."),

    NOT_FOUND_ELECTION("NOT_FOUND_ELECTION", "Election '%s' does not exist"),

    ELECTION_NOT_STARTED("ELECTION_NOT_STARTED", "Election '%s' has not started yet, wait until %s"),

    ELECTION_ALREADY_FINISHED("ELECTION_ALREADY_FINISHED", "Election '%s' is already finished since %s");

    private String value;

    private String defaultMessage;

    ErrorCode(String value, String defaultMessage) {
        this.value = value;
        this.defaultMessage = defaultMessage;
    }

    public String getValue() {
        return value;
    }

    public String getDefaultMessage() {
        return defaultMessage;
    }
}
