package com.apamatesoft.validator.constants;

import static com.apamatesoft.validator.constants.RegularExpression.*;
import static java.lang.Double.parseDouble;
import static java.util.regex.Pattern.compile;

public class Validators {

    /**
     * Validate that the String is different from null and empty.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean required(String evaluate) {
        if (evaluate==null) return false;
        return !evaluate.isEmpty();
    }

    /**
     * Validate that the length of the String is equal to the condition.
     * @param evaluate String to evaluate.
     * @param condition Character length.
     * @return true if it meets the condition.
     */
    public static boolean length(String evaluate, int condition) {
        if (!required(evaluate)) return false;
        return evaluate.length()==condition;
    }

    /**
     * Validate that the length of the String is less than the condition.
     * @param evaluate String to evaluate.
     * @param condition Minimum character length.
     * @return true if it meets the condition.
     */
    public static boolean minLength(String evaluate, int condition) {
        if (!required(evaluate)) return false;
        return condition<evaluate.length();
    }

    /**
     * Validate that the length of the String is greater than the condition.
     * @param evaluate String to evaluate.
     * @param condition Maximum character length.
     * @return true if it meets the condition.
     */
    public static boolean maxLength(String evaluate, int condition) {
        if (!required(evaluate)) return false;
        return condition>evaluate.length();
    }

    /**
     * Validate that the String matches the regular expression.
     * @param evaluate String to evaluate.
     * @param regExp Regular expression.
     * @return true if it meets the condition.
     */
    public static boolean regExp(String evaluate, String regExp) {
        if (!required(evaluate)) return false;
        return compile(regExp).matcher(evaluate).find();
    }

    /**
     * Validate that the String has an email format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean email(String evaluate) {
        return regExp(evaluate, EMAIL);
    }

    /**
     * Validate that the string is a number format. <br/>
     * <b>Note:</b> This includes so many integers, decimal, and negative values.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean number(String evaluate) {
        return regExp(evaluate, DECIMAL);
    }

    public static boolean link(String evaluate) {
        return regExp(evaluate, LINK);
    }

    public static boolean wwwLink(String evaluate) {
        return regExp(evaluate, WWW_LINK);
    }

    public static boolean httpLink(String evaluate) {
        return regExp(evaluate, HTTP_LINK);
    }

    public static boolean httpsLink(String evaluate) {
        return regExp(evaluate, HTTPS_LINK);
    }

    public static boolean ip(String evaluate) {
        return regExp(evaluate, IP);
    }

    public static boolean ipv4(String evaluate) {
        return regExp(evaluate, IPV4);
    }

    public static boolean ipv6(String evaluate) {
        return regExp(evaluate, IPV6);
    }

    /**
     * Validate that the String only contains characters included in the condition.
     * @param evaluate String to evaluate.
     * @param condition String with allowed characters.
     * @return true if it meets the condition.
     */
    public static boolean shouldOnlyContain(String evaluate, String condition) {
        if (!required(evaluate)) return false;
        for (char a: evaluate.toCharArray()) {
            if (!condition.contains(String.valueOf(a))) return false;
        }
        return true;
    }

    /**
     * Validate that the String contains only numeric characters.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean onlyNumbers(String evaluate) {
        return regExp(evaluate, ONLY_NUMBERS);
    }

    /**
     * Validate that the String does not contain any characters included in the condition.
     * @param evaluate String to evaluate.
     * @param condition String with invalid characters.
     * @return true if it meets the condition.
     */
    public static boolean notContain(String evaluate, String condition) {
        if (!required(evaluate)) return false;
        for (char a: condition.toCharArray()) {
            if (evaluate.contains(a+"")) return false;
        }
        return true;
    }

    /**
     * Validate that the String contains at least one character included in the condition.
     * @param evaluate String to evaluate.
     * @param condition String with desired characters.
     * @return true if it meets the condition.
     */
    public static boolean mustContainOne(String evaluate, String condition) {
        if (!required(evaluate)) return false;
        for (char a: condition.toCharArray()) {
            if (evaluate.contains(a+"")) return true;
        }
        return false;
    }

    public static boolean gt(String evaluate, double condition) {
        if (!required(evaluate) || !number(evaluate)) return false;
        return condition> parseDouble(evaluate);
    }

    public static boolean gte(String evaluate, double condition) {
        if (!required(evaluate) || !number(evaluate)) return false;
        return condition>= parseDouble(evaluate);
    }

    public static boolean ls(String evaluate, double condition) {
        if (!required(evaluate) || !number(evaluate)) return false;
        return condition< parseDouble(evaluate);
    }

    public static boolean lse(String evaluate, double condition) {
        if (!required(evaluate) || !number(evaluate)) return false;
        return condition<=parseDouble(evaluate);
    }

    public static boolean equal(String evaluate, String condition) {
        if (!required(evaluate)) return false;
        return condition.equals(evaluate);
    }

}
