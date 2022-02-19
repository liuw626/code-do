package com.godric.cd.utils;

import java.util.Random;

public class CodeUtil {

    private static final Random R = new Random();

    private static final Integer DIGIT = 6;

    /**
     * 生成 6 位随机码
     */
    public static String randomCode() {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<DIGIT; i++) {
            sb.append(R.nextInt(10));
        }
        return sb.toString();
    }

}
