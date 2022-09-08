package com.apamatesoft.validator.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;
import static com.apamatesoft.validator.constants.RegularExpression.*;
import static java.lang.Double.parseDouble;
import static java.util.regex.Pattern.compile;

// TODO: Date, Cambiar de paquete
public class Validators {

    //<editor-fold desc="LENGTH VALIDATORS">

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
        return evaluate.length()>=condition;
    }

    /**
     * Validate that the length of the String is greater than the condition.
     * @param evaluate String to evaluate.
     * @param condition Maximum character length.
     * @return true if it meets the condition.
     */
    public static boolean maxLength(String evaluate, int condition) {
        if (!required(evaluate)) return false;
        return evaluate.length()<=condition;
    }

    //</editor-fold>

    //<editor-fold desc="FORMAT VALIDATION">

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
     * Validate that the String is a number format. <br/>
     * Note: This includes so many integers, decimal, and negative values.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean number(String evaluate) {
        return regExp(evaluate, DECIMAL);
    }

    /**
     * Validate that the String is a link format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean link(String evaluate) {
        return regExp(evaluate, LINK);
    }

    /**
     * Validate that the String is a link with www format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean wwwLink(String evaluate) {
        return regExp(evaluate, WWW_LINK);
    }

    /**
     * Validate that the String is a link with http format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean httpLink(String evaluate) {
        return regExp(evaluate, HTTP_LINK);
    }

    /**
     * Evaluate that the text matches the pattern, replacing the x's with numbers.
     * <br/> <br/>
     * <b>Example:</b>
     * <br/>
     * For the pattern +xx (xxx) xxx-xx-xx, the following Strings are valid:
     * <ul>
     *     <li>+12 (345) 678-90-12</li>
     *     <li>+11 (111) 111-11-11</li>
     *     <li>+xx (345) 678-90-12</li>
     *     <li>+xx (xxx) xxx-xx-xx</li>
     * <ul/>
     * @param evaluate String to evaluate.
     * @param pattern String with the pattern.
     * @return true if it meets the condition.
     */
    public static boolean numberPattern(String evaluate, String pattern) {
        if (evaluate==null || pattern==null) return false;
        if (evaluate.length()!=pattern.length()) return false;
        for (int i = 0; i < pattern.length(); i++) {
            char patternChar = pattern.charAt(i);
            char evaluateChar = evaluate.charAt(i);
            if (patternChar=='x' || patternChar == 'X') {
                if (!onlyNumbers( String.valueOf(evaluateChar)) )
                    if (patternChar!=evaluateChar)
                        return false;
            } else {
                if (patternChar!=evaluateChar) return false;
            }
        }
        return true;
    }

    //</editor-fold>

    //<editor-fold desc="CONTENT VALIDATIONS">

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
        return regExp(evaluate, NUMBERS);
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

    /**
     * Validate that the value of the String is not greater than the condition.
     * @param evaluate String to evaluate.
     * @param condition Maximum value.
     * @return true if it meets the condition.
     */
    public static boolean maxValue(String evaluate, double condition) {
        if (!required(evaluate) || !number(evaluate)) return false;
        return parseDouble(evaluate)>condition;
    }

    /**
     * Validate that the value of the String is not less than the condition.
     * @param evaluate String to evaluate.
     * @param condition Minimum value.
     * @return true if it meets the condition.
     */
    public static boolean minValue(String evaluate, double condition) {
        if (!required(evaluate) || !number(evaluate)) return false;
        return parseDouble(evaluate)<condition;
    }

    public static boolean rangeValue(String evaluate, double min, double max) {
        if (!required(evaluate) || !number(evaluate)) return false;
        double value = parseDouble(evaluate);
        return (value >= min) && (value<=max);
    }
    //</editor-fold>

    // TODO:
    //  - Rule test

    /**
     * Validates that the text to evaluate matches the specified date format.
     * @param evaluate String to evaluate.
     * @param format Describing the date and time format.
     * @return true if it meets the condition.
     */
    public static boolean dateFormat(String evaluate, String format) {
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setLenient(false);
        try {
            sdf.parse(evaluate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String is a link with http format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean httpsLink(String evaluate) {
        return regExp(evaluate, HTTPS_LINK);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String is an ip format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean ip(String evaluate) {
        return regExp(evaluate, IP);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String is an ip with ipv4 format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean ipv4(String evaluate) {
        return regExp(evaluate, IPV4);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String is an ip with ipv6 format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean ipv6(String evaluate) {
        return regExp(evaluate, IPV6);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String is a time format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean time(String evaluate) {
        return regExp(evaluate, TIME);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String is a time with 12 hours format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean time12(String evaluate) {
        return regExp(evaluate, TIME12);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String is a time with 24 hours format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean time24(String evaluate) {
        return regExp(evaluate, TIME24);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    public static boolean phone(String evaluate) {
        return regExp(evaluate, PHONE);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String contains only alphabetic characters.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean onlyCharacters(String evaluate) {
        return regExp(evaluate, ALPHABET);
    }

    // TODO:
    //  - Description
    //  - Validators test
    //  - Messages
    //  - add Rule
    //  - add Rule builder
    //  - Rule description
    //  - Rule test
    /**
     * Validate that the String contains only alphanumeric characters.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean onlyAlphanumeric(String evaluate) {
        return regExp(evaluate, ALPHA_NUMERIC);
    }

}
