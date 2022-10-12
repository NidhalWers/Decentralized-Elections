package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testBuildBlock(){
        Block block = Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
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
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the second block")
                .build();

        String expectedHash = "40ce09dfd2b3f5b51654b4c56c1e564dbb4a29ab50d7ffcd4dbc0fb8fc643e5d7344d2da5d1c42d0b847091ecdcae0ccf43d06432a817bfc097af3f9e2d50688";

        assertThat(block.getHash()).isEqualTo(expectedHash);
    }

    @Test
    void testIsValid() {
        Block block = Block.builder()
                .previousHash("hash-1")
                .timeStamp(LocalDateTime.of(2022,10,30,8,30,00))
                .data("this is the second block")
                .build();

        assertThat(block.isValid()).isTrue();

    }
}