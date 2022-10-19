package com.septgrandcorsaire.blockchain.api.error.exception;

public enum ErrorCode {
    INVALID_PARAMETER("INVALID_PARAMETER", "Parameter '%s' is invalid."),

    REQUIRED_PARAMETER("REQUIRED_PARAMETER", "Parameter '%s' is required.");
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
