package io.github.ApamateSoft.validator.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import static io.github.ApamateSoft.validator.utils.RegularExpression.*;
import static java.lang.Double.parseDouble;
import static java.util.regex.Pattern.compile;

public class Validators {

    //<editor-fold default-state="collapsed" desc="LENGTH VALIDATORS">
    /**
     * Validates that the String is different from null and empty.
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

    /**
     * Validates that the length of the String is between a minimum and a maximum.
     * @param evaluate String to evaluate.
     * @param min Minimum character length.
     * @param max Maximum character length.
     * @return true if it meets the condition.
     */
    public static boolean rangeLength(String evaluate, int min, int max) {
        if (!required(evaluate)) return false;
        return (evaluate.length() >= min) && (evaluate.length() <= max);
    }
    //</editor-fold">

    //<editor-fold default-state="collapsed" desc="FORMAT VALIDATION">

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
     * Validate that the String is a link with https format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean httpsLink(String evaluate) {
        return regExp(evaluate, HTTPS_LINK);
    }

    /**
     * Validate that the String is an ip format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean ip(String evaluate) {
        return regExp(evaluate, IP);
    }

    /**
     * Validate that the String is an ip with ipv4 format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean ipv4(String evaluate) {
        return regExp(evaluate, IPV4);
    }

    /**
     * Validate that the String is an ip with ipv6 format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean ipv6(String evaluate) {
        return regExp(evaluate, IPV6);
    }

    /**
     * Validate that the String is a time format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean time(String evaluate) {
        return regExp(evaluate, TIME);
    }

    /**
     * Validate that the String is a time with 12 hours format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean time12(String evaluate) {
        return regExp(evaluate, TIME12);
    }

    /**
     * Validate that the String is a time with 24 hours format.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean time24(String evaluate) {
        return regExp(evaluate, TIME24);
    }

    /**
     * Evaluate that the text matches the pattern, replacing the x's with numbers.
     * <br/> <br/>
     * <b>Example:</b>
     * <br/>
     * For the pattern +xx (xxx) xxx-xx-xx, the following Strings are valid:
     * <ul>
     *     <li>+12 (345) 678-90-12</li>
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

    /**
     * Validates that the text to evaluate matches the specified date format.
     * @param evaluate String to evaluate.
     * @param format Describing the date and time format.
     * @return true if it meets the condition.
     */
    public static boolean dateFormat(String evaluate, String format) {
        if (!required(evaluate)) return false;
        final SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        if (evaluate.length()!=format.length()) return false;
        sdf.setLenient(false);
        try {
            sdf.parse(evaluate);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Validates that the String is a proper name. <br/>
     * <b>Note:</b>
     * <ul>
     *     <li>Capitalization is ignored</li>
     *     <li>
     *         Only valid proper names in English. to evaluate names in other languages it is recommended to use the
     *         {@link #regExp(String, String)} function.
     *     </li>
     * <ul/>
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean name(String evaluate) {
        return regExp(evaluate, NAME);
    }
    //</editor-fold">

    //<editor-fold default-state="collapsed" desc="CONTENT VALIDATIONS">

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

    // TODO: Agregar advertencia indicando que solo caracteres del alfabeto ingles.
    /**
     * Validate that the String contains only alphabetic characters.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean onlyCharacters(String evaluate) {
        return regExp(evaluate, ALPHABET);
    }

    /**
     * Validate that the String contains only alphanumeric characters.
     * @param evaluate String to evaluate.
     * @return true if it meets the condition.
     */
    public static boolean onlyAlphanumeric(String evaluate) {
        return regExp(evaluate, ALPHA_NUMERIC);
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
        return parseDouble(evaluate)<=condition;
    }

    // TODO: Recomendar utilizar number format antes de esta regla.
    // TODO
    /**
     * Validate that the value of the String is not less than the condition.
     * @param evaluate String to evaluate.
     * @param condition Minimum value.
     * @return true if it meets the condition.
     */
    public static boolean minValue(String evaluate, double condition) {
        if (!required(evaluate) || !number(evaluate)) return false;
        return parseDouble(evaluate)>=condition;
    }

    /**
     * Validate that the value of the String is in the established range.
     * @param evaluate String to evaluate.
     * @param min minimum value.
     * @param max maximum value.
     * @return true if it meets the condition.
     */
    public static boolean rangeValue(String evaluate, double min, double max) {
        if (!required(evaluate) || !number(evaluate)) return false;
        double value = parseDouble(evaluate);
        return (value >= min) && (value <= max);
    }

    /**
     * Validates that the period from the entered date to the current date is greater than or equal to a minimum age.
     * <br/>
     * <b>warning: </b>
     * This function makes use of the current date of the device.
     * @param evaluate String to evaluate.
     * @param format Describing the date and time format.
     * @param age minimum age.
     * @return true if it meets the condition.
     */
    public static boolean minAge(String evaluate, String format, int age) {
        if (!required(evaluate)) return false;
        if (!dateFormat(evaluate, format)) return false;
        LocalDate now = new Date()
            .toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setLenient(false);
        try {
            LocalDate evaluateDate = sdf.parse(evaluate).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            Period period = evaluateDate.until(now);
            int periodInYears = period.getYears();
            return periodInYears >= age;
        } catch (ParseException e) {
            return false;
        }
    }

    /**
     * Valid that the entered date has not expired.
     * <br/>
     * <b>warning: </b>
     * This function makes use of the current date of the device.
     * @param evaluate String to evaluate.
     * @param format Describing the date and time format.
     * @return true if it meets the condition.
     */
    public static boolean expirationDate(String evaluate, String format) {
        if (!required(evaluate)) return false;
        if (!dateFormat(evaluate, format)) return false;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
        sdf.setLenient(false);
        try {
            Date evaluateDate = sdf.parse(evaluate);
            return now.getTime() > evaluateDate.getTime();
        } catch (ParseException e) {
            return false;
        }
    }

    // TODO: Reemplazar la expresion regular por una lista de carcacteres permitidos.
    /**
     * Valid that a regular expression repeats a minimum amount.
     * @param evaluate Valid that the entered date has not expired.
     * @param regExp Regular expression.
     * @param min minimum value.
     * @return true if it meets the condition.
     */
    public static boolean mustContainMinimum(String evaluate, String regExp, int min) {
        if (!required(evaluate)) return false;
        int count = 0;
        for (char c : evaluate.toCharArray())
            if (compile(regExp).matcher(c+"").find())
                ++count;
        return count >= min;
    }
    //</editor-fold">

}
