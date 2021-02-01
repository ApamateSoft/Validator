package com.apamatesoft.validator.constants;

public class Constants {

    public static final String EMAIL_RE = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";

    public static final String NUMBER = "0123456789";

    public static final String ALPHABET = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    public static final String NAME = ALPHABET+" ";
    public static final String ALPHA_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHA_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHA_NUMERIC = NUMBER+ALPHABET;
    public static final String ALPHA_NUMERIC_LOWERCASE = NUMBER+ALPHA_LOWERCASE;
    public static final String ALPHA_NUMERIC_UPPERCASE = NUMBER+ALPHA_UPPERCASE;

    public static final String ALPHABET_ES = "aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚvVwWxXyYzZ";
    public static final String NAME_ES = ALPHABET_ES+" ";
    public static final String ALPHA_LOWERCASE_ES = "aábcdeéfghiíjklmnñoópqrstuúvwxyz";
    public static final String ALPHA_UPPERCASE_ES = "AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ";
    public static final String ALPHA_NUMERIC_ES = NUMBER+ALPHABET_ES;
    public static final String ALPHA_NUMERIC_LOWERCASE_ES = NUMBER+ALPHA_LOWERCASE_ES;
    public static final String ALPHA_NUMERIC_UPPERCASE_ES = NUMBER+ALPHA_UPPERCASE_ES;

}