// TODO:
//  - Contener al menos n cantidad de caracteres.?
//  - mayor a cierta edad.
//  - password?
//  - RegEgx: [
//  - nombre *,
//  - credit card, (number, expiration, cvv) *
//  - filepath]
//  - Eliminar el metodo any o simplificar el uso.
package com.apamatesoft.validator;

import com.apamatesoft.validator.constants.Validators;
import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.messages.Messages;
import com.apamatesoft.validator.messages.MessagesEn;
import com.apamatesoft.validator.functions.OnInvalidEvaluation;
import com.apamatesoft.validator.functions.Validate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.String.format;

/**
 * <h1>Validator</h1>
 *
 * Validator is a library written in Java, which aims to simplify the validation of Strings by declaring a series of
 * rules.
 *
 * @author ApamateSoft
 * @version 1.2.0
 */
public class Validator implements Cloneable {

    private static Messages messages = new MessagesEn();

    private final List<Rule> rules = new ArrayList<>();
    private OnInvalidEvaluation onInvalidEvaluation;
    private String notMatchMessage = messages.getNotMatchMessage();

    // <editor-fold default-state="collapsed" defaulted="collapsed" desc="CONSTRUCTORS">
    public Validator() { }

    private Validator(Builder builder) {
        rules.addAll(builder.rules);
        onInvalidEvaluation = builder.onInvalidEvaluation;
        notMatchMessage = builder.notMatchMessage;
    }
    //</editor-fold>

    /**
     * Sets the default error messages for each of the pre-established rules.
     * @param messages error messages.
     */
    public static void setMessages(Messages messages) {
        if (messages==null) return;
        Validator.messages = messages;
    }

    public static Messages getMessages() {
        return messages;
    }

    /**
     * Validate that the String to evaluate meets all the rules.<br>
     * <b>Note:</b> If the String does not meet any rule, the {@link #onInvalidEvaluation(OnInvalidEvaluation)} event will be invoked with the
     * corresponding error message.
     * @param evaluate String to evaluate.
     * @return true: if validation passes.
     */
    public boolean isValid(String evaluate) {
        for (Rule rule: rules) {
            if (!rule.validate(evaluate)) {
                if (onInvalidEvaluation !=null) onInvalidEvaluation.invoke(rule.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * Validate that the String to evaluate meets all the rules.<br>
     * @param evaluate String to evaluate.
     * @throws InvalidEvaluationException Exception thrown if the String to evaluate does not meet any rule.
     */
    public void isValidOrFail(String evaluate) throws InvalidEvaluationException {
        for (Rule rule: rules)
            if (!rule.validate(evaluate))
                throw new InvalidEvaluationException(rule.getMessage(), evaluate);
    }

    /**
     * Validate that both Strings match and that they meet all the rules. <br/>
     * <b>Note:</b>
     * <ul>
     *     <li>
     *         If the Strings do not comply with some of the rules, the onNotPass {@link #onInvalidEvaluation(OnInvalidEvaluation)} event will
     *         be invoked, with the corresponding error message.
     *     </li>
     *     <li>
     *        An error message can be set in case the comparison fails with the {@link #setNotMatchMessage(String)}
     *        method.
     *     </li>
     * <ul/>
     * @param evaluate String to evaluate.
     * @param compare String to compare.
     * @return true: if validation passes.
     */
    public boolean compare(String evaluate, String compare) {
        if (!evaluate.equals(compare)) {
            if (onInvalidEvaluation !=null) onInvalidEvaluation.invoke(notMatchMessage);
            return false;
        }
        return isValid(evaluate);
    }

    /**
     * Validate that both Strings match and that they meet all the rules.
     * <br/><br/>
     * <b>Note:</b>
     * <ul>
     *     <li>
     *         If the Strings do not comply with some of the rules, the onNotPass {@link #onInvalidEvaluation(OnInvalidEvaluation)} event will
     *         be invoked, with the corresponding error message.
     *     </li>
     *     <li>
     *        An error message can be set in case the comparison fails with the {@link #setNotMatchMessage(String)}
     *        method.
     *     </li>
     * <ul/>
     * @param evaluate String to evaluate.
     * @param compare String to compare.
     * @throws InvalidEvaluationException Exception thrown if the String to evaluate does not meet any rule.
     */
    public void compareOrFail(String evaluate, String compare) throws InvalidEvaluationException {
        if (!evaluate.equals(compare))
            throw new InvalidEvaluationException(notMatchMessage, evaluate);
        isValidOrFail(evaluate);
    }

    /**
     * Sets the error message to display, in case the String comparison fails in the method
     * {@link #compare(String, String)}.
     * @param message Error message.
     */
    public void setNotMatchMessage(String message) {
        this.notMatchMessage = message;
    }

    //<editor-fold default-state="collapsed" desc="RULES">

    /**
     * Create a validation rule.
     * <br/><br/>
     * <b>Example:<b/><br>
     * <code>
     * <pre>
     * new Validator().rule("The text is different from 'xxx'", evaluate -> {
     *     return evaluate.equals("xxx");
     * });
     * </pre>
     * </code>
     * @param message Error message.
     * @param validate Function that returns true when the String to evaluate meets the conditions.
     */
    public void rule(String message, Validate validate) {
        rules.add(new Rule(message, validate));
    }

    /**
     * Validate that the value passes at least one of the validators
     * @param message Error message.
     * @param validators Validators.
     */
    public void any(String message, Validate... validators) {
        rule(message, it -> Arrays.stream(validators).anyMatch( validate -> validate.invoke(it) ) );
    }

    //<editor-fold default-state="collapsed" desc="LENGTH RULES">

    //<editor-fold default-state="collapsed" desc="required">
    /**
     * Validate that the String to evaluate is different from empty and null.
     * @param message Error message.
     */
    public void required(String message) {
        rule(message, Validators::required);
    }

    /**
     * Validate that the String to evaluate is different from empty and null.
     */
    public void required() {
        required(messages.getRequireMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="length">
    /**
     * Validate that the String to evaluate has the exact length of characters to the condition.
     * @param condition character length.
     * @param message Error message.
     */
    public void length(int condition, String message) {
        rule(format(message, condition), it -> Validators.length(it, condition) );
    }

    /**
     * Validate that the String to evaluate has the exact length of characters to the condition.
     * @param condition character length.
     */
    public void length(int condition) {
        length(condition, messages.getLengthMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="minLength">
    /**
     * Validate that the String to evaluate has a minimum character length to the condition.
     * @param condition Minimum character length.
     * @param message Error message.
     */
    public void minLength(int condition, String message) {
        rule(format(message, condition), it -> Validators.minLength(it, condition) );
    }

    /**
     * Validate that the String to evaluate has a minimum character length to the condition.
     * @param condition Minimum character length.
     */
    public void minLength(int condition) {
        minLength(condition, messages.getMinLengthMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="maxLength">
    /**
     * Validate that the String to evaluate has a maximum length of characters to the condition.
     * @param condition maximum character length.
     * @param message Error message.
     */
    public void maxLength(int condition, String message) {
        rule(format(message, condition), it -> Validators.maxLength(it, condition) );
    }

    /**
     * Validate that the String to evaluate has a maximum length of characters to the condition.
     * @param condition maximum character length.
     */
    public void maxLength(int condition) {
        maxLength(condition, messages.getMaxLengthMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="rangeLength">
    /**
     * valid that the length of the String is between the minimum and the maximum.
     * @param min Minimum character length.
     * @param max Maximum character length.
     * @param message Error message.
     */
    public void rangeLength(int min, int max, String message) {
        rule(format(message, min, max), it -> Validators.rangeLength(it, min, max) );
    }

    /**
     * valid that the length of the String is between the minimum and the maximum.
     * @param min Minimum character length.
     * @param max Maximum character length.
     */
    public void rangeLength(int min, int max) {
        rangeLength(min, max, messages.getRangeLengthMessage());
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="FORMAT RULES">

    //<editor-fold default-state="collapsed" desc="regExp">
    /**
     * Validate that the value matches the regular expression.
     * @param condition Regular expression
     * @param message Error message.
     */
    public void regExp(String condition, String message) {
        rule(format(message, condition), it -> Validators.regExp(it, condition) );
    }

    /**
     * Validate that the value matches the regular expression.
     * @param condition Regular expression
     */
    public void regExp(String condition) {
        regExp(condition, messages.getRegExpMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="email">
    /**
     * Validate that the String has an email format.
     * @param message Error message.
     */
    public void email(String message) {
        rule(message, Validators::email);
    }

    /**
     * Validate that the String has an email format
     */
    public void email() {
        email(messages.getEmailMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="number">
    /**
     * Validate that the String is in numeric format.
     * @param message Error message.
     */
    public void number(String message) {
        rule(message, Validators::number);
    }

    /**
     * Validate that the String is in numeric format.
     */
    public void number() {
        number(messages.getNumberMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="link">
    /**
     * Validate that the String is a link format.
     * @param message Error message.
     */
    public void link(String message) {
        rule(message, Validators::link);
    }

    /**
     * Validate that the String is a link format.
     */
    public void link() {
        link(messages.getLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="wwwLink">
    /**
     * Validate that the String is a link with www format.
     * @param message Error message.
     */
    public void wwwLink(String message) {
        rule(message, Validators::wwwLink);
    }

    /**
     * Validate that the String is a link with www format.
     */
    public void wwwLink() {
        wwwLink(messages.getWwwLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="httpLink">
    /**
     * Validate that the String is a link with http format.
     * @param message Error message.
     */
    public void httpLink(String message) {
        rule(message, Validators::httpLink);
    }

    /**
     * Validate that the String is a link with http format.
     */
    public void httpLink() {
        httpLink(messages.getHttpLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="httpsLink">
    /**
     * Validate that the String is a link with https format.
     * @param message Error message.
     */
    public void httpsLink(String message) {
        rule(message, Validators::httpsLink);
    }

    /**
     * Validate that the String is a link with https format.
     */
    public void httpsLink() {
        httpsLink(messages.getHttpsLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="ip">
    /**
     * Validate that the String is an ip format.
     * @param message Error message.
     */
    public void ip(String message) {
        rule(message, Validators::ip);
    }

    /**
     * Validate that the String is an ip format.
     */
    public void ip() {
        ip(messages.getIpMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="ipv4">
    /**
     * Validate that the String is an ip with ipv4 format.
     * @param message Error message.
     */
    public void ipv4(String message) {
        rule(message, Validators::ipv4);
    }

    /**
     * Validate that the String is an ip with ipv4 format.
     */
    public void ipv4() {
        ipv4(messages.getIpv4Message());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="ipv6">
    /**
     * Validate that the String is an ip with ipv6 format.
     * @param message Error message.
     */
    public void ipv6(String message) {
        rule(message, Validators::ipv6);
    }

    /**
     * Validate that the String is an ip with ipv6 format.
     */
    public void ipv6() {
        ipv6(messages.getIpv6Message());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="time">
    /**
     * Validate that the String is a time format.
     * @param message Error message.
     */
    public void time(String message) {
        rule(message, Validators::time);
    }

    /**
     * Validate that the String is a time format.
     */
    public void time() {
        time(messages.getTimeMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="time12">
    /**
     * Validate that the String is a time with 12 hours format.
     * @param message Error message.
     */
    public void time12(String message) {
        rule(message, Validators::time12);
    }

    /**
     * Validate that the String is a time with 12 hours format.
     */
    public void time12() {
        time12(messages.getTime12Message());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="time24">
    /**
     * Validate that the String is a time with 24 hours format.
     * @param message Error message.
     */
    public void time24(String message) {
        rule(message, Validators::time24);
    }

    /**
     * Validate that the String is a time with 24 hours format.
     */
    public void time24() {
        time24(messages.getTime24Message());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="onlyCharacters">
    /**
     * Validate that the String contains only alphabetic characters.
     * @param message Error message.
     */
    public void onlyCharacters(String message) {
        rule(message, Validators::onlyCharacters);
    }

    /**
     * Validate that the String contains only alphabetic characters.
     */
    public void onlyCharacters() {
        onlyCharacters(messages.getOnlyCharactersMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="onlyAlphanumeric">
    /**
     * Validate that the String contains only alphanumeric characters.
     * @param message Error message.
     */
    public void onlyAlphanumeric(String message) {
        rule(message, Validators::onlyAlphanumeric);
    }

    /**
     * Validate that the String contains only alphanumeric characters.
     */
    public void onlyAlphanumeric() {
        onlyAlphanumeric(messages.getOnlyAlphanumericMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="numberPattern">
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
     * @param pattern String with the pattern.
     * @param message Error message.
     */
    public void numberPattern(String pattern, String message) {
        rule(format(message, pattern), it -> Validators.numberPattern(it, pattern) );
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
     * @param pattern String with the pattern.
     */
    public void numberPattern(String pattern) {
        numberPattern(pattern, messages.getNumberPatternMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="dateFormat">
    /**
     * Validates that the text to evaluate matches the specified date format.
     * @param format Describing the date and time format.
     * @param message Error message.
     */
    public void dateFormat(String format, String message) {
        rule(format(message, format), it -> Validators.dateFormat(it, format) );
    }

    /**
     * Validates that the text to evaluate matches the specified date format.
     * @param format Describing the date and time format.
     */
    public void dateFormat(String format) {
        dateFormat(format, messages.getDateFormatMessage());
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="CONTENT RULES">

    //<editor-fold default-state="collapsed" desc="shouldOnlyContain">
    /**
     * Validate that the String only contains characters included in the condition String.
     * @param condition String with allowed characters.
     * @param message  Error message.
     */
    public void shouldOnlyContain(String condition, String message) {
        rule(format(message, condition), it -> Validators.shouldOnlyContain(it, condition) );
    }

    /**
     * Validate that the String only contains characters included in the condition String.
     * @param condition String with allowed characters.
     */
    public void shouldOnlyContain(String condition) {
        shouldOnlyContain(condition, messages.getShouldOnlyContainMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="onlyNumbers">
    /**
     * Validate that the String contains only numeric characters.
     * @param message Error message.
     */
    public void onlyNumbers(String message) {
        rule(message, Validators::onlyNumbers);
    }

    /**
     * Validate that the String contains only numeric characters.
     */
    public void onlyNumbers() {
        onlyNumbers(messages.getOnlyNumbersMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="notContain">
    /**
     * Validate that the String does not contain any character included in the String of the condition.
     * @param condition String with invalid characters.
     * @param message Error message.
     */
    public void notContain(String condition, String message) {
        rule(format(message, condition), it -> Validators.notContain(it, condition) );
    }

    /**
     * Validate that the String does not contain any character included in the String of the condition.
     * @param condition String with invalid characters.
     */
    public void notContain(String condition) {
        notContain(condition, messages.getNotContainMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="mustContainOne">
    /**
     * Validates that the String contains at least one character included in the String of the condition.
     * @param condition String with desired characters.
     * @param message Error message.
     */
    public void mustContainOne(String condition, String message) {
        rule(format(message, condition), it -> Validators.mustContainOne(it, condition) );
    }

    /**
     * Validates that the String contains at least one character included in the String of the condition.
     * @param condition String with desired characters.
     */
    public void mustContainOne(String condition) {
        mustContainOne(condition, messages.getMustContainOneMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="maxValue">
    /**
     * Validate that the value of the String is not greater than the condition.
     * @param condition maximum value.
     * @param message Error message.
     */
    public void maxValue(double condition, String message) {
        rule(format(message, condition), it -> Validators.maxValue(it, condition) );
    }

    /**
     * Validate that the value of the String is not greater than the condition.
     * @param condition maximum value.
     */
    public void maxValue(double condition) {
        maxValue(condition, messages.getMaxValueMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="minValue">
    /**
     * Validate that the value of the String is not less than the condition.
     * @param condition minimum value.
     * @param message Error message.
     */
    public void minValue(double condition, String message) {
        rule(format(message, condition), it -> Validators.minValue(it, condition) );
    }

    /**
     * Validate that the value of the String is not less than the condition.
     * @param condition minimum value.
     */
    public void minValue(double condition) {
        minValue(condition, messages.getMinValueMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="rangeValue">

    /**
     * Validates that the value of the string is in the established range.
     * @param min minimum value.
     * @param max maximum value
     * @param message Error message.
     */
    public void rangeValue(double min, double max, String message) {
        rule( format(message, min, max), it -> Validators.rangeValue(it, min, max) );
    }

    /**
     * Validates that the value of the string is in the established range.
     * @param min minimum value.
     * @param max maximum value
     */
    public void rangeValue(double min, double max) {
        rangeValue(min, max, messages.getRangeValueMessage());
    }
    //</editor-fold>

    //</editor-fold>

    //</editor-fold>

    /**
     * Event that is invoked when some rule is not fulfilled.
     * @param onInvalidEvaluation Function with the error message.
     */
    public void onInvalidEvaluation(OnInvalidEvaluation onInvalidEvaluation) {
        this.onInvalidEvaluation = onInvalidEvaluation;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Create a copy of the Validator object.
     * @return copy of Validator.
     */
    public Validator copy() {
        try {
            return (Validator) this.clone();
        } catch (Exception ignored) {
            return null;
        }
    }

    /**
     * Class that allows to build a Validator in a sequential and centralized way.
     */
    public static class Builder {

        private final List<Rule> rules = new ArrayList<>();
        private OnInvalidEvaluation onInvalidEvaluation;
        private String notMatchMessage = messages.getNotMatchMessage();

        /**
         * Sets the error message to display, in case the String comparison fails in the method
         * {@link #compare(String, String)}.
         * @param message Error message.
         * @return Builder
         */
        public Builder setNotMatchMessage(String message) {
            this.notMatchMessage = message;
            return this;
        }

        //<editor-fold default-state="collapsed" desc="RULES">

        /**
         * Create a validation rule.
         * <br/><br/>
         * <b>Ejemplo:<b/><br>
         * <code>
         * <pre>
         * new Validator().rule("The text is different from 'xxx'", evaluate -> {
         *     return evaluate.equals("xxx");
         * });
         * </pre>
         * </code>
         *
         * @param message Error message.
         * @param validate Function that returns true when the String to evaluate meets the conditions.
         * @return Builder
         */
        public Builder rule(String message, Validate validate) {
            rules.add(new Rule(message, validate));
            return this;
        }

        /**
         * Validate that the value passes at least one of the validators.
         * @param message Error message.
         * @param validators Validators.
         * @return Builder
         */
        public Builder any(String message, Validate... validators) {
            return rule(message, it -> Arrays.stream(validators).anyMatch( validate -> validate.invoke(it) ) );
        }

        //<editor-fold default-state="collapsed" desc="LENGTH RULES">

        //<editor-fold default-state="collapsed" desc="required">
        /**
         * Validate that the String to evaluate is different from empty and null.
         * @param message Error message.
         * @return Builder
         */
        public Builder required(String message) {
            return rule(message, Validators::required);
        }

        /**
         * Validate that the String to evaluate is different from empty and null.
         * @return Builder
         */
        public Builder required() {
            return required(messages.getRequireMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="length">
        /**
         * Validate that the String to evaluate has the exact length of characters to the condition.
         * @param condition character length.
         * @param message Error message.
         * @return Builder
         */
        public Builder length(int condition, String message) {
            return rule(format(message, condition), it -> Validators.length(it, condition) );
        }

        /**
         * Validate that the String to evaluate has the exact length of characters to the condition.
         * @param condition character length.
         * @return Builder
         */
        public Builder length(int condition) {
            return length(condition, messages.getLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="minLength">
        /**
         * Validate that the String to evaluate has a minimum character length to the condition.
         * @param condition Minimum character length.
         * @param message Error message.
         * @return Builder
         */
        public Builder minLength(int condition, String message) {
            return rule(format(message, condition), it -> Validators.minLength(it, condition) );
        }

        /**
         * Validate that the String to evaluate has a minimum character length to the condition.
         * @param condition Minimum character length.
         * @return Builder
         */
        public Builder minLength(int condition) {
            return minLength(condition, messages.getMinLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="maxLength">
        /**
         * Validate that the String to evaluate has a maximum length of characters to the condition.
         * @param condition maximum character length.
         * @param message Error message.
         * @return Builder
         */
        public Builder maxLength(int condition, String message) {
            return rule(format(message, condition), it -> Validators.maxLength(it, condition) );
        }

        /**
         * Validate that the String to evaluate has a maximum length of characters to the condition.
         * @param condition maximum character length.
         * @return Builder
         */
        public Builder maxLength(int condition) {
            return maxLength(condition, messages.getMaxLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="rangeLength">
        /**
         * valid that the length of the String is between the minimum and the maximum.
         * @param min Minimum character length.
         * @param max Maximum character length.
         * @param message Error message.
         * @return Builder
         */
        public Builder rangeLength(int min, int max, String message) {
            return rule(format(message, min, max), it -> Validators.rangeLength(it, min, max) );
        }

        /**
         * valid that the length of the String is between the minimum and the maximum.
         * @param min Minimum character length.
         * @param max Maximum character length.
         * @return Builder
         */
        public Builder rangeLength(int min, int max) {
            return rangeLength(min, max, messages.getRangeLengthMessage());
        }
        //</editor-fold>

        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="FORMAT RULES">

        //<editor-fold default-state="collapsed" desc="regExp">
        /**
         * Validate that the value matches the regular expression.
         * @param condition Regular expression
         * @param message Error message.
         * @return Builder
         */
        public Builder regExp(String condition, String message) {
            return rule(format(message, condition), it -> Validators.regExp(it, condition) );
        }

        /**
         * Validate that the value matches the regular expression.
         * @param condition Regular expression
         * @return Builder
         */
        public Builder regExp(String condition) {
            return regExp(condition, messages.getRegExpMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="email">
        /**
         * Validate that the String has an email format
         * @param message Error message.
         * @return Builder
         */
        public Builder email(String message) {
            return rule(message, Validators::email);
        }

        /**
         * Validate that the String has an email format
         * @return Builder
         */
        public Builder email() {
            return email(messages.getEmailMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="number">
        /**
         * Validate that the String is in numeric format.
         * @param message Error message.
         * @return Builder
         */
        public Builder number(String message) {
            return rule(message, Validators::number);
        }

        /**
         * Validate that the String is in numeric format.
         * @return Builder
         */
        public Builder number() {
            return number(messages.getNumberMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="link">
        /**
         * Validate that the String is a link format.
         * @param message Error message.
         * @return Builder
         */
        public Builder link(String message) {
            return rule(message, Validators::link);
        }

        /**
         * Validate that the String is a link format.
         * @return Builder
         */
        public Builder link() {
            return link(messages.getLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="wwwLink">
        /**
         * Validate that the String is a link with www format.
         * @param message Error message.
         * @return Builder
         */
        public Builder wwwLink(String message) {
            return rule(message, Validators::wwwLink);
        }

        /**
         * Validate that the String is a link with www format.
         * @return Builder
         */
        public Builder wwwLink() {
            return wwwLink(messages.getWwwLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="httpLink">
        /**
         * Validate that the String is a link with http format.
         * @param message Error message.
         * @return Builder
         */
        public Builder httpLink(String message) {
            return rule(message, Validators::httpLink);
        }

        /**
         * Validate that the String is a link with http format.
         * @return Builder
         */
        public Builder httpLink() {
            return httpLink(messages.getHttpLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="httpsLink">
        /**
         * Validate that the String is a link with https format.
         * @param message Error message.
         * @return Builder
         */
        public Builder httpsLink(String message) {
            return rule(message, Validators::httpsLink);
        }

        /**
         * Validate that the String is a link with https format.
         * @return Builder
         */
        public Builder httpsLink() {
            return httpsLink(messages.getHttpsLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="ip">
        /**
         * Validate that the String is an ip format.
         * @param message Error message.
         * @return Builder
         */
        public Builder ip(String message) {
            return rule(message, Validators::ip);
        }

        /**
         * Validate that the String is an ip format.
         * @return Builder
         */
        public Builder ip() {
            return ip(messages.getIpMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="ipv4">
        /**
         * Validate that the String is an ip with ipv4 format.
         * @param message Error message.
         * @return Builder
         */
        public Builder ipv4(String message) {
            return rule(message, Validators::ipv4);
        }

        /**
         * Validate that the String is an ip with ipv4 format.
         * @return Builder
         */
        public Builder ipv4() {
            return ipv4(messages.getIpv4Message());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="ipv6">
        /**
         * Validate that the String is an ip with ipv6 format.
         * @param message Error message.
         * @return Builder
         */
        public Builder ipv6(String message) {
            return rule(message, Validators::ipv6);
        }

        /**
         * Validate that the String is an ip with ipv6 format.
         * @return Builder
         */
        public Builder ipv6() {
            return ipv6(messages.getIpv6Message());
        }

        //<editor-fold default-state="collapsed" desc="time">
        /**
         * Validate that the String is a time format.
         * @param message Error message.
         * @return Builder
         */
        public Builder time(String message) {
            return rule(message, Validators::time);
        }

        /**
         * Validate that the String is a time format.
         * @return Builder
         */
        public Builder time() {
            return time(messages.getTimeMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="time12">
        /**
         * Validate that the String is a time with 12 hours format.
         * @param message Error message.
         * @return Builder
         */
        public Builder time12(String message) {
            return rule(message, Validators::time12);
        }

        /**
         * Validate that the String is a time with 12 hours format.
         * @return Builder
         */
        public Builder time12() {
            return time12(messages.getTime12Message());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="time24">
        /**
         * Validate that the String is a time with 24 hours format.
         * @param message Error message.
         * @return Builder
         */
        public Builder time24(String message) {
            return rule(message, Validators::time24);
        }

        /**
         * Validate that the String is a time with 24 hours format.
         * @return Builder
         */
        public Builder time24() {
            return time24(messages.getTime24Message());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="onlyCharacters">
        /**
         * Validate that the String contains only alphabetic characters.
         * @param message Error message.
         * @return Builder
         */
        public Builder onlyCharacters(String message) {
            return rule(message, Validators::onlyCharacters);
        }

        /**
         * Validate that the String contains only alphabetic characters.
         * @return Builder
         */
        public Builder onlyCharacters() {
            return onlyCharacters(messages.getOnlyCharactersMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="onlyAlphanumeric">
        /**
         * Validate that the String contains only alphanumeric characters.
         * @param message Error message.
         * @return Builder
         */
        public Builder onlyAlphanumeric(String message) {
            return rule(message, Validators::onlyAlphanumeric);
        }

        /**
         * Validate that the String contains only alphanumeric characters.
         * @return Builder
         */
        public Builder onlyAlphanumeric() {
            return onlyAlphanumeric(messages.getOnlyAlphanumericMessage());
        }
        //</editor-fold>

        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="numberPattern">
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
         * @param pattern String with the pattern.
         * @param message Error message.
         * @return Builder
         */
        public Builder numberPattern(String pattern, String message) {
            return rule(format(message, pattern), it -> Validators.numberPattern(it, pattern) );
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
         * @param pattern String with the pattern.
         * @return Builder
         */
        public Builder numberPattern(String pattern) {
            return numberPattern(pattern, messages.getNumberPatternMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="dateFormat">
        /**
         * Validates that the text to evaluate matches the specified date format.
         * @param format Describing the date and time format.
         * @param message Error message.
         * @return Builder
         */
        public Builder dateFormat(String format, String message) {
            return rule(format(message, format), it -> Validators.dateFormat(it, format) );
        }

        /**
         * Validates that the text to evaluate matches the specified date format.
         * @param format Describing the date and time format.
         * @return Builder
         */
        public Builder dateFormat(String format) {
            return dateFormat(format, messages.getDateFormatMessage());
        }
        //</editor-fold>

        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="CONTENT RULES">

        //<editor-fold default-state="collapsed" desc="shouldOnlyContain">
        /**
         * Validate that the String only contains characters included in the condition String.
         * @param condition String with allowed characters.
         * @param message  Error message.
         * @return Builder
         */
        public Builder shouldOnlyContain(String condition, String message) {
            return rule(format(message, condition), it -> Validators.shouldOnlyContain(it, condition));
        }

        /**
         * Validate that the String only contains characters included in the condition String.
         * @param condition String with allowed characters.
         * @return Builder
         */
        public Builder shouldOnlyContain(String condition) {
            return shouldOnlyContain(condition, messages.getShouldOnlyContainMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="onlyNumbers">
        /**
         * Validate that the String contains only numeric characters.
         * @param message Error message.
         * @return Builder
         */
        public Builder onlyNumbers(String message) {
            return rule(message, Validators::onlyNumbers);
        }

        /**
         * Validate that the String contains only numeric characters.
         * @return Builder
         */
        public Builder onlyNumbers() {
            return onlyNumbers(messages.getOnlyNumbersMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="notContain">
        /**
         * Validate that the String does not contain any character included in the String of the condition.
         * @param condition String with invalid characters.
         * @param message Error message.
         * @return Builder
         */
        public Builder notContain(String condition, String message) {
            return rule(format(message, condition), it -> Validators.notContain(it, condition));
        }

        /**
         * Validate that the String does not contain any character included in the String of the condition.
         * @param condition String with invalid characters.
         * @return Builder
         */
        public Builder notContain(String condition) {
            return notContain(condition, messages.getNotContainMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="mustContainOne">
        /**
         * Validates that the String contains at least one character included in the String of the condition.
         * @param condition String with desired characters.
         * @param message Error message.
         * @return Builder
         */
        public Builder mustContainOne(String condition, String message) {
            return rule(format(message, condition), it -> Validators.mustContainOne(it, condition));
        }

        /**
         * Validates that the String contains at least one character included in the String of the condition.
         * @param condition String with desired characters.
         * @return Builder
         */
        public Builder mustContainOne(String condition) {
            return mustContainOne(condition, messages.getMustContainOneMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="max">
        /**
         * Validate that the value of the String is not greater than the condition.
         * @param condition maximum value.
         * @param message Error message.
         * @return Builder
         */
        public Builder maxValue(double condition, String message) {
            return rule(format(message, condition), it -> Validators.maxValue(it, condition) );
        }

        /**
         * Validate that the value of the String is not greater than the condition.
         * @param condition maximum value.
         * @return Builder
         */
        public Builder maxValue(double condition) {
            return maxValue(condition, messages.getMaxValueMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="min">
        /**
         * Validate that the value of the String is not less than the condition.
         * @param condition minimum value.
         * @param message Error message.
         * @return Builder
         */
        public Builder minValue(double condition, String message) {
            return rule(format(message, condition), it -> Validators.minValue(it, condition) );
        }

        /**
         * Validate that the value of the String is not less than the condition.
         * @param condition minimum value.
         * @return Builder
         */
        public Builder minValue(double condition) {
            return minValue(condition, messages.getMinValueMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="rangeValue">
        /**
         * Validates that the value of the string is in the established range.
         * @param min minimum value.
         * @param max maximum value
         * @param message Error message.
         * @return Builder
         */
        public Builder rangeValue(double min, double max, String message) {
            return rule( format(message, min, max), it -> Validators.rangeValue(it, min, max) );
        }

        /**
         * Validates that the value of the string is in the established range.
         * @param min minimum value.
         * @param max maximum value
         * @return Builder
         */
        public Builder rangeValue(double min, double max) {
            return rangeValue(min, max, messages.getRangeValueMessage());
        }
        //</editor-fold>

        //</editor-fold>

        //</editor-fold>

        /**
         * Event that is invoked when some rule is not fulfilled.
         * @param onInvalidEvaluation Function with the error message.
         * @return Builder
         */
        public Builder onInvalidEvaluation(OnInvalidEvaluation onInvalidEvaluation) {
            this.onInvalidEvaluation = onInvalidEvaluation;
            return this;
        }

        /**
         * Build the Validator
         * @return Validator
         */
        public Validator build() {
            return new Validator(this);
        }

    }

}