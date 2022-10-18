package com.septgrandcorsaire.blockchain.model;

import com.septgrandcorsaire.blockchain.model.Block;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
        assertThat(block.getData()).isEqualTo("this is the second block");
    }

    @Test
    void testComputeHash() {
        Block block = Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022, 10, 30, 8, 30, 00))
                .data("this is the second block")
                .build();

        String expectedHash = "814366bf6ae693808da58f58e0a608cd7e3c763e00d7120b1836cad546056e68f88c7e21d79fc7b835ef137ad9a55c29ea2321470e4067abb8ef841fa67047f2";

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