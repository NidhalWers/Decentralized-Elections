package com.septgrandcorsaire.blockchain.service;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
class CryptographyServiceTest {

    @Test
    void testComputeAlgorithmSha256() {
        String input = "Hello World, this is a test of SHA-256";
        String expected = "465e49aa82871cefe9a9fe482c42e16f80ffcd2ee6cd4a4d463766742eed0ac4";

        assertThat(CryptographyService.computeAlgorithmSha256(input)).isEqualTo(expected);
    }

    @Test
    void testComputeAlgorithmSha3() {
        String input = "Hello World, this is a test of SHA-3";
        String expected = "2ec22ea5671babebd7529fdf28cc31d2ea710b0c1ecbcbd3cb62028a2171209afabd682568bbb4918be93b7aae11c0862dfe75b8f8f33a3d19c49711f2b354de";

        assertThat(CryptographyService.computeAlgorithmSha3(input)).isEqualTo(expected);
    }
}