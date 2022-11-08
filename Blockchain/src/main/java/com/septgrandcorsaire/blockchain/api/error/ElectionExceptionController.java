package com.septgrandcorsaire.blockchain.api.error;

import com.septgrandcorsaire.blockchain.api.error.exception.*;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import static com.septgrandcorsaire.blockchain.Application.LOGGER;


@ControllerAdvice
public class ElectionExceptionController {


    @ExceptionHandler(value = IllegalPayloadArgumentException.class)
    public ResponseEntity<ErrorResource> illegalPayloadArgumentHandler(IllegalPayloadArgumentException exception) {
        LOGGER.error(String.format("%s %s Stacktrace : %s", exception.getMessage(), exception.getCause(), ExceptionUtils.getStackTrace(exception)));
        return new ResponseEntity<>(new ErrorResource(exception.getCode().getValue(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResource> illegalArgumentHandler(IllegalArgumentException exception) {
        LOGGER.error(String.format("%s %s Stacktrace : %s", exception.getMessage(), exception.getCause(), ExceptionUtils.getStackTrace(exception)));
        return new ResponseEntity<>(new ErrorResource(ErrorCode.INVALID_PARAMETER.getValue(), exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = ElectionNotFoundException.class)
    public ResponseEntity<ErrorResource> electionNotFoundHandler(ElectionNotFoundException exception) {
        LOGGER.error(String.format("%s %s Stacktrace : %s", exception.getMessage(), exception.getCause(), ExceptionUtils.getStackTrace(exception)));
        return new ResponseEntity<>(new ErrorResource(exception.getCode().getValue(), exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = ElectionAlreadyFinishedException.class)
    public ResponseEntity<ErrorResource> electionAlreadyFinishedHandler(ElectionAlreadyFinishedException exception) {
        LOGGER.error(String.format("%s %s Stacktrace : %s", exception.getMessage(), exception.getCause(), ExceptionUtils.getStackTrace(exception)));
        return new ResponseEntity<>(new ErrorResource(exception.getCode().getValue(), exception.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = ElectionNotStartedException.class)
    public ResponseEntity<ErrorResource> electionNotStartedHandler(ElectionNotStartedException exception) {
        LOGGER.error(String.format("%s %s Stacktrace : %s", exception.getMessage(), exception.getCause(), ExceptionUtils.getStackTrace(exception)));
        return new ResponseEntity<>(new ErrorResource(exception.getCode().getValue(), exception.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = VoterHasAlreadyVotedException.class)
    public ResponseEntity<ErrorResource> voterHasAlreadyVotedHandler(VoterHasAlreadyVotedException exception) {
        LOGGER.error(String.format("%s %s Stacktrace : %s", exception.getMessage(), exception.getCause(), ExceptionUtils.getStackTrace(exception)));
        return new ResponseEntity<>(new ErrorResource(exception.getCode().getValue(), exception.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(value = InvalidApiKeyException.class)
    public ResponseEntity<ErrorResource> invalidApiKeyHandler(InvalidApiKeyException exception) {
        LOGGER.error(String.format("%s %s Stacktrace : %s", exception.getMessage(), exception.getCause(), ExceptionUtils.getStackTrace(exception)));
        return new ResponseEntity<>(new ErrorResource(exception.getCode().getValue(), exception.getMessage()), HttpStatus.METHOD_NOT_ALLOWED);
    }
}
