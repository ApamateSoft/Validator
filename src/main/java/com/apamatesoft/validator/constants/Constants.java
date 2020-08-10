package com.apamatesoft.validator.constants;

public class Constants {
    public static String NUMBER = "0123456789";

    public static String ALPHABET = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    public static String NAME = ALPHABET+" ";
    public static String ALPHA_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static String ALPHA_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static String ALPHA_NUMERIC = NUMBER+ALPHABET;
    public static String ALPHA_NUMERIC_LOWERCASE = NUMBER+ALPHA_LOWERCASE;
    public static String ALPHA_NUMERIC_UPPERCASE = NUMBER+ALPHA_UPPERCASE;

    public static String ALPHABET_ES = "aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚvVwWxXyYzZ";
    public static String NAME_ES = ALPHABET_ES+" ";
    public static String ALPHA_LOWERCASE_ES = "aábcdeéfghiíjklmnñoópqrstuúvwxyz";
    public static String ALPHA_UPPERCASE_ES = "AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ";
    public static String ALPHA_NUMERIC_ES = NUMBER+ALPHABET_ES;
    public static String ALPHA_NUMERIC_LOWERCASE_ES = NUMBER+ALPHA_LOWERCASE_ES;
    public static String ALPHA_NUMERIC_UPPERCASE_ES = NUMBER+ALPHA_UPPERCASE_ES;
}
