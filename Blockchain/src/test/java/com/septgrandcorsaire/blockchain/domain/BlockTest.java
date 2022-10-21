package com.septgrandcorsaire.blockchain.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
class BlockTest {

    @Test
    void testBuildBlock() {
        Block block = Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 30, 00))
                .data("this is the second block")
                .build();

        assertThat(block.getPreviousHash()).isEqualTo("hash-1");
        assertThat(block.getTimeStamp().getYear()).isEqualTo(2022);
        assertThat(block.getTimeStamp().getMonthValue()).isEqualTo(10);
        assertThat(block.getTimeStamp().getDayOfMonth()).isEqualTo(30);
        assertThat(block.getTimeStamp().getHour()).isEqualTo(8);
        assertThat(block.getTimeStamp().getMinute()).isEqualTo(30);
        assertThat(block.getTimeStamp().getSecond()).isEqualTo(0);
        assertThat(block.getData()).isInstanceOf(StringData.class);
        assertThat(((StringData) block.getData()).getValue()).isEqualTo("this is the second block");
    }

    @Test
    void testComputeHash() {
        Block block = Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 30, 00))
                .data("this is the second block")
                .build();

        String expectedHash = "da923f6dc9cc82474efcdd9e46a4d5fb5167212dfda31038d7d36e37262802fd76d62172a2b9c37299083315e31cee94e63f6e5d6860f49e6cc49c3aebd92404";

        assertThat(block.getHash()).isEqualTo(expectedHash);
    }

    @Test
    void testIsValid() {
        Block block = Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 30, 00))
                .data("this is the second block")
                .build();

        assertThat(block.isValid()).isTrue();

    }
}