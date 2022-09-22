package com.company.util;

import java.util.Random;

public class RandomUtil {

    private static Random random = new Random();


    public static String getRandomSmsCode() {
        int n = random.nextInt(89999) + 10000; // 10000-99999
        // 0 - 89999   + 10000  ->  10000 - 99999
        return String.valueOf(n);
    }
}
