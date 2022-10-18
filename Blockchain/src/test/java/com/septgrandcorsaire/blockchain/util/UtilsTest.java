package com.septgrandcorsaire.blockchain.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
class UtilsTest {

    @Test
    void testGetStringOfZeros() {
        final int numberOfZerosExpected = 5;
        final String expected = "00000";
        String actual = Utils.getStringOfZeros(5);

        assertThat(actual).isEqualTo(expected);
    }
}