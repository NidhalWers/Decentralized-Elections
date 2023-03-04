package com.septgrandcorsaire.blockchain.api.error.exception;

public enum ErrorCode {
    INVALID_PARAMETER("INVALID_PARAMETER", "Parameter '%s' is invalid."),

    INVALID_BOOLEAN_FORMAT("INVALID_BOOLEAN_FORMAT", "Parameter '%s' is not in a boolean format : should be true or false"),

    REQUIRED_PARAMETER("REQUIRED_PARAMETER", "Parameter '%s' is required."),

    NOT_FOUND_ELECTION("NOT_FOUND_ELECTION", "Election '%s' does not exist"),

    ELECTION_NOT_STARTED("ELECTION_NOT_STARTED", "Election '%s' has not started yet, wait until %s"),

    ELECTION_ALREADY_FINISHED("ELECTION_ALREADY_FINISHED", "Election '%s' is already finished since %s"),

    BAD_DATE_FORMAT("BAD_DATE_FORMAT", "The '%s' parameter should be in [YY-MM-DD'T'HH:mm::ss] format"),

    HAS_ALREADY_VOTED("HAS_ALREADY_VOTED", "%s has already voted for the election named '%s'"),

    INVALID_API_KEY("INVALID_API_KEY", "This API Key cannot be used for the '%s' election. Any attempt at fraud will be fought back !"),

    ELECTION_NOT_FINISHED("ELECTION_NOT_FINISHED", "Action not permitted until the end of %s election");

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
