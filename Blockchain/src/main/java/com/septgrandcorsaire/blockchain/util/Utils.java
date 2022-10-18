package com.septgrandcorsaire.blockchain.util;

/**
 * @author Nidhal TEYEB
 * @since 0.0.1-SNAPSHOT
 */
public class Utils {

    public static String getStringOfZeros(int numberOfZeros) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < numberOfZeros; i++) {
            builder.append("0");
        }
        return builder.toString();
    }
}
