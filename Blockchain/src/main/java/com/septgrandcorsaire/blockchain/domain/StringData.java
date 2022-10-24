package com.septgrandcorsaire.blockchain.domain;

import com.septgrandcorsaire.blockchain.infrastructure.service.JsonService;
import lombok.Value;

@Value
public class StringData implements Data {

    private String value;

    public static StringData of(String value) {
        return new StringData(value);
    }

    @Override
    public String toString() {
        return JsonService.toJson(this);
    }
}
