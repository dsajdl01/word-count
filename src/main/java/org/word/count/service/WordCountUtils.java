package org.word.count.service;

public class WordCountUtils
{
    public static String trimValue(String value) {
        return value == null ? null : value.strip();
    }
}
