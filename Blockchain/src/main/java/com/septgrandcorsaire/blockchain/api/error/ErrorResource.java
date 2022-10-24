package com.septgrandcorsaire.blockchain.api.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Wai-Ki WONG
 * @since 0.1
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResource {

    private String code;

    private String message;
}
