package com.apamatesoft.validator.constants;

import java.util.regex.Pattern;

import static com.apamatesoft.validator.constants.Constants.EMAIL_RE;
import static com.apamatesoft.validator.constants.Constants.NUMBER;

public class Validators {

    public static boolean required(String evaluate) {
        if (evaluate==null) return false;
        return !evaluate.isEmpty();
    }

    public static boolean length(String evaluate, int condition) {
        if (evaluate==null) return false;
        return evaluate.length()==condition;
    }

    public static boolean minLength(String evaluate, int condition) {
        if (evaluate==null) return false;
        return evaluate.length()>=condition;
    }

    public static boolean maxLength(String evaluate, int condition) {
        if (evaluate==null) return false;
        return evaluate.length()<=condition;
    }

    public static boolean email(String evaluate) {
        if (evaluate==null) return false;
        return Pattern.compile(EMAIL_RE).matcher(evaluate).find();
    }

    public static boolean numericFormat(String evaluate) {
        if (evaluate==null) return false;
        try {
            double number = Double.parseDouble(evaluate);
            return !Double.isNaN(number);
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean shouldOnlyContain(String evaluate, String condition) {
        if (evaluate==null) return false;
        if (evaluate.isEmpty()) return false;
        for (char a: evaluate.toCharArray()) {
            if (!condition.contains(String.valueOf(a))) return false;
        }
        return true;
    }

    public static boolean onlyNumbers(String evaluate) {
        if (evaluate==null) return false;
        return shouldOnlyContain(evaluate, NUMBER);
    }

    public static boolean notContain(String evaluate, String condition) {
        if (evaluate==null) return false;
        if (evaluate.isEmpty()) return false;
        for (char a: condition.toCharArray()) {
            if (evaluate.contains(a+"")) return false;
        }
        return true;
    }

    public static boolean mustContainOne(String evaluate, String condition) {
        if (evaluate==null) return false;
        for (char a: condition.toCharArray()) {
            if (evaluate.contains(a+"")) return true;
        }
        return false;
    }

}
