package model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void buildBlock(){
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

}