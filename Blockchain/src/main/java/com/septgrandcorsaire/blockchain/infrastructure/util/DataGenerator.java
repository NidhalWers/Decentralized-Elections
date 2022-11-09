package com.septgrandcorsaire.blockchain.infrastructure.util;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DataGenerator {

    public static LocalDateTime newDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate) {
        List<LocalDateTime> listOfDateTimes = listOfDateTimesBetween(startDate, endDate);
        int randomIndex = (int) (Math.random() * listOfDateTimes.size());
        return listOfDateTimes.get(randomIndex);
    }

    private static List<LocalDateTime> listOfDateTimesBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return Stream.iterate(startDate, d -> d.plusMinutes(1))
                .limit(ChronoUnit.MINUTES.between(startDate, endDate))
                .collect(Collectors.toList());
    }

    public static String chooseAValueRandomlyInAList(List<String> list) {
        int randomIndex = (int) (Math.random() * list.size());
        return list.get(randomIndex);
    }


}
