package com.septgrandcorsaire.blockchain.api.error.exception;

public class IllegalPayloadArgumentException extends SmartVoteException {

    public IllegalPayloadArgumentException(ErrorCode code, String s) {
        super(s, code);
    }

    public static IllegalPayloadArgumentException ofErrorCode(ErrorCode code, String parameterName) {
        return new IllegalPayloadArgumentException(
                code,
                String.format(code.getDefaultMessage(), parameterName));
    }
}
