package com.septgrandcorsaire.blockchain.infrastructure.util;

import com.septgrandcorsaire.blockchain.api.error.exception.ErrorCode;
import com.septgrandcorsaire.blockchain.api.error.exception.IllegalPayloadArgumentException;

import java.time.LocalDateTime;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class DateTimeParser {

    public static LocalDateTime parseDateTime(String parameter, String parameterName) {
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(parameter);
        } catch (Exception e) {
            throw new IllegalPayloadArgumentException(ErrorCode.BAD_DATE_FORMAT, String.format(ErrorCode.BAD_DATE_FORMAT.getDefaultMessage(), parameterName));
        }
        return dateTime;
    }
}
