package com.ntouzidis.cooperative.module.common.utils;

public class RandomGenerator {

    public static String generate() {
        return String.valueOf(Math.random() * Math.random() * Math.random() * 100000000);
    }
}
