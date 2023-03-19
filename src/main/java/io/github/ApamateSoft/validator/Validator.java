package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.utils.Validators;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.messages.Messages;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import io.github.ApamateSoft.validator.functions.Validate;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

/**
 * <h1>Validator</h1>
 *
 * Facilitates the validation of Strings by chaining a series of rules
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
     * Validate that both Strings match and that they meet all the rules <br/>
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

//    /**
//     * Validate that the value passes at least one of the validators
//     * @param message Error message.
//     * @param validators Validators.
//     */
//    public void any(String message, Validate... validators) {
//        rule(message, it -> Arrays.stream(validators).anyMatch( validate -> validate.invoke(it) ) );
//    }

    //<editor-fold default-state="collapsed" desc="LENGTH RULES">

    //<editor-fold default-state="collapsed" desc="required">
    /**
     * Validates that the String is different from null and empty
     * @param message Error message
     */
    public void required(String message) {
        rule(message, Validators::required);
    }

    /**
     * Validates that the String is different from null and empty
     */
    public void required() {
        required(messages.getRequireMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="length">
    /**
     * Validates that the String has an exact length of characters
     * @param condition character length
     * @param message Error message
     */
    public void length(int condition, String message) {
        rule(format(message, condition), it -> Validators.length(it, condition) );
    }

    /**
     * Validates that the String has an exact length of characters
     * @param condition character length
     */
    public void length(int condition) {
        length(condition, messages.getLengthMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="minLength">
    /**
     * Validates that the length of the String is not less than the condition
     * @param condition Minimum character length
     * @param message Error message
     */
    public void minLength(int condition, String message) {
        rule(format(message, condition), it -> Validators.minLength(it, condition) );
    }

    /**
     * Validates that the length of the String is not less than the condition
     * @param condition Minimum character length
     */
    public void minLength(int condition) {
        minLength(condition, messages.getMinLengthMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="maxLength">
    /**
     * Validates that the length of the String is not greater than the condition
     * @param condition maximum character length
     * @param message Error message
     */
    public void maxLength(int condition, String message) {
        rule(format(message, condition), it -> Validators.maxLength(it, condition) );
    }

    /**
     * Validates that the length of the String is not greater than the condition
     * @param condition maximum character length
     */
    public void maxLength(int condition) {
        maxLength(condition, messages.getMaxLengthMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="rangeLength">
    /**
     * Validates that the length of the String is in the established range
     * @param min Minimum character length
     * @param max Maximum character length
     * @param message Error message
     */
    public void rangeLength(int min, int max, String message) {
        rule(format(message, min, max), it -> Validators.rangeLength(it, min, max) );
    }

    /**
     * Validates that the length of the String is in the established range
     * @param min Minimum character length
     * @param max Maximum character length
     */
    public void rangeLength(int min, int max) {
        rangeLength(min, max, messages.getRangeLengthMessage());
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="FORMAT RULES">

    //<editor-fold default-state="collapsed" desc="regExp">
    /**
     * Validates that the String matches the regular expression
     * @param condition Regular expression
     * @param message Error message
     */
    public void regExp(String condition, String message) {
        rule(format(message, condition), it -> Validators.regExp(it, condition) );
    }

    /**
     * Validates that the String matches the regular expression
     * @param condition Regular expression
     */
    public void regExp(String condition) {
        regExp(condition, messages.getRegExpMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="email">
    /**
     * Validates that the String has an email format
     * @param message Error message
     */
    public void email(String message) {
        rule(message, Validators::email);
    }

    /**
     * Validates that the String has an email format
     */
    public void email() {
        email(messages.getEmailMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="number">
    /**
     * Validates that the String is a numeric format <br/>
     * <b>Note:</b> This includes so many integers, decimal, and negative values.
     * @param message Error message
     */
    public void number(String message) {
        rule(message, Validators::number);
    }

    /**
     * Validates that the String is a numeric format <br/>
     * <b>Note:</b> This includes so many integers, decimal, and negative values
     */
    public void number() {
        number(messages.getNumberMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="link">
    /**
     * Validates that the String is a link format
     * @param message Error message
     */
    public void link(String message) {
        rule(message, Validators::link);
    }

    /**
     * Validates that the String is a link format
     */
    public void link() {
        link(messages.getLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="wwwLink">
    /**
     * Validates that the String is a link with www format
     * @param message Error message
     */
    public void wwwLink(String message) {
        rule(message, Validators::wwwLink);
    }

    /**
     * Validates that the String is a link with www format
     */
    public void wwwLink() {
        wwwLink(messages.getWwwLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="httpLink">
    /**
     * Validates that the String is a link with http format
     * @param message Error message
     */
    public void httpLink(String message) {
        rule(message, Validators::httpLink);
    }

    /**
     * Validates that the String is a link with http format
     */
    public void httpLink() {
        httpLink(messages.getHttpLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="httpsLink">
    /**
     * Validates that the String is a link with https format
     * @param message Error message
     */
    public void httpsLink(String message) {
        rule(message, Validators::httpsLink);
    }

    /**
     * Validates that the String is a link with https format
     */
    public void httpsLink() {
        httpsLink(messages.getHttpsLinkMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="ip">
    /**
     * Validates that the String is an ip format
     * @param message Error message
     */
    public void ip(String message) {
        rule(message, Validators::ip);
    }

    /**
     * Validates that the String is an ip format
     */
    public void ip() {
        ip(messages.getIpMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="ipv4">
    /**
     * Validates that the String is an ipv4 format
     * @param message Error message
     */
    public void ipv4(String message) {
        rule(message, Validators::ipv4);
    }

    /**
     * Validates that the String is an ipv4 format
     */
    public void ipv4() {
        ipv4(messages.getIpv4Message());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="ipv6">
    /**
     * Validates that the String is an ipv6 format
     * @param message Error message
     */
    public void ipv6(String message) {
        rule(message, Validators::ipv6);
    }

    /**
     * Validates that the String is an ipv6 format
     */
    public void ipv6() {
        ipv6(messages.getIpv6Message());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="time">
    /**
     * Validates that the String is a time format
     * @param message Error message
     */
    public void time(String message) {
        rule(message, Validators::time);
    }

    /**
     * Validates that the String is a time format
     */
    public void time() {
        time(messages.getTimeMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="time12">
    /**
     * Validates that the String is a time with 12-hour format
     * @param message Error message
     */
    public void time12(String message) {
        rule(message, Validators::time12);
    }

    /**
     * Validates that the String is a time with 12-hour format
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

    //<editor-fold default-state="collapsed" desc="numberPattern">
    /**
     * Validates that the String matches the pattern, replacing the x's with numbers <br/>
     * <b>Example:</b> For the pattern +xx (xxx) xxx-xx-xx, the following Strings are valid:
     * <ul>
     *     <li>+12 (345) 678-90-12</li>
     *     <li>+xx (345) 678-90-12</li>
     *     <li>+xx (xxx) xxx-xx-xx</li>
     * <ul/>
     * @param pattern String with the pattern
     * @param message Error message
     */
    public void numberPattern(String pattern, String message) {
        rule(format(message, pattern), it -> Validators.numberPattern(it, pattern) );
    }

    /**
     * Validates that the String matches the pattern, replacing the x's with numbers <br/>
     * <b>Example:</b> For the pattern +xx (xxx) xxx-xx-xx, the following Strings are valid:
     * <ul>
     *     <li>+12 (345) 678-90-12</li>
     *     <li>+xx (345) 678-90-12</li>
     *     <li>+xx (xxx) xxx-xx-xx</li>
     * <ul/>
     * @param pattern String with the pattern
     */
    public void numberPattern(String pattern) {
        numberPattern(pattern, messages.getNumberPatternMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="dateFormat">
    /**
     * Validates that the String to evaluate matches the specified date format
     * @param format Describing the date and time format
     * @param message Error message
     */
    public void dateFormat(String format, String message) {
        rule(format(message, format), it -> Validators.dateFormat(it, format) );
    }

    /**
     * Validates that the String to evaluate matches the specified date format
     * @param format Describing the date and time format
     */
    public void dateFormat(String format) {
        dateFormat(format, messages.getDateFormatMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="name">
    /**
     * Validates that the String is a proper name <br/>
     * <b>Note:</b>
     * <ul>
     *     <li>Capitalization is ignored</li>
     *     <li>
     *         Only valid proper names in English. to evaluate names in other languages it is recommended to use the
     *         {@link #regExp(String, String)} function.
     *     </li>
     * <ul/>
     * @param message Error message.
     */
    public void name(String message) {
        rule(message, Validators::name);
    }

    /**
     * Validates that the String is a proper name <br/>
     * <b>Note:</b>
     * <ul>
     *     <li>Capitalization is ignored</li>
     *     <li>
     *         Only valid proper names in English. to evaluate names in other languages it is recommended to use the
     *         {@link #regExp(String)} function.
     *     </li>
     * <ul/>
     */
    public void name() {
        name(messages.getNameMessage());
    }
    //</editor-fold>

    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="CONTENT RULES">

    //<editor-fold default-state="collapsed" desc="onlyLetters">
    /**
     * Validates that the String contains only letters <br />
     * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
     * {@link #shouldOnlyContain(String, String)} function
     * @param message Error message
     */
    public void onlyLetters(String message) {
        rule(message, Validators::onlyLetters);
    }

    /**
     * Validates that the String contains only letters <br />
     * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
     * {@link #shouldOnlyContain(String)} function
     */
    public void onlyLetters() {
        onlyLetters(messages.getOnlyLettersMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="onlyAlphanumeric">
    /**
     * Validates that the String contains only alphanumeric characters <br />
     * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
     * {@link #shouldOnlyContain(String, String)} function
     * @param message Error message.
     */
    public void onlyAlphanumeric(String message) {
        rule(message, Validators::onlyAlphanumeric);
    }

    /**
     * Validates that the String contains only alphanumeric characters <br />
     * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
     * {@link #shouldOnlyContain(String)} function
     */
    public void onlyAlphanumeric() {
        onlyAlphanumeric(messages.getOnlyAlphanumericMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="shouldOnlyContain">
    /**
     * Validates that the String only contains characters included in the condition
     * @param condition String with allowed characters
     * @param message  Error message
     */
    public void shouldOnlyContain(String condition, String message) {
        rule(format(message, condition), it -> Validators.shouldOnlyContain(it, condition) );
    }

    /**
     * Validates that the String only contains characters included in the condition
     * @param condition String with allowed characters
     */
    public void shouldOnlyContain(String condition) {
        shouldOnlyContain(condition, messages.getShouldOnlyContainMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="onlyNumbers">
    /**
     * Validates that the String to evaluate only contains numeric characters
     * @param message Error message
     */
    public void onlyNumbers(String message) {
        rule(message, Validators::onlyNumbers);
    }

    /**
     * Validates that the String to evaluate only contains numeric characters
     */
    public void onlyNumbers() {
        onlyNumbers(messages.getOnlyNumbersMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="notContain">
    /**
     * Validates that the String does not contain any characters included in the condition
     * @param condition String with invalid characters
     * @param message Error message
     */
    public void notContain(String condition, String message) {
        rule(format(message, condition), it -> Validators.notContain(it, condition) );
    }

    /**
     * Validates that the String does not contain any characters included in the condition
     * @param condition String with invalid characters
     */
    public void notContain(String condition) {
        notContain(condition, messages.getNotContainMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="mustContainOne">
    /**
     * Validates that the String contains at least one character included in the condition
     * @param condition String with desired characters
     * @param message Error message
     */
    public void mustContainOne(String condition, String message) {
        rule(format(message, condition), it -> Validators.mustContainOne(it, condition) );
    }

    /**
     * Validates that the String contains at least one character included in the condition
     * @param condition String with desired characters
     */
    public void mustContainOne(String condition) {
        mustContainOne(condition, messages.getMustContainOneMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="maxValue">
    /**
     * Validates that the value of the String is not greater than the condition <br />
     * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
     * @param condition maximum value
     * @param message Error message
     */
    public void maxValue(double condition, String message) {
        rule(format(message, condition), it -> Validators.maxValue(it, condition) );
    }

    /**
     * Validates that the value of the String is not greater than the condition <br />
     * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
     * @param condition maximum value.
     */
    public void maxValue(double condition) {
        maxValue(condition, messages.getMaxValueMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="minValue">
    /**
     * Validates that the value of the String is not less than the condition <br />
     * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
     * @param condition minimum value
     * @param message Error message
     */
    public void minValue(double condition, String message) {
        rule(format(message, condition), it -> Validators.minValue(it, condition) );
    }

    /**
     * Validates that the value of the String is not less than the condition <br />
     * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
     * @param condition minimum value.
     */
    public void minValue(double condition) {
        minValue(condition, messages.getMinValueMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="rangeValue">

    /**
     * Validates that the value of the String is in the established range <br />
     * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
     * @param min minimum value
     * @param max maximum value
     * @param message Error message
     */
    public void rangeValue(double min, double max, String message) {
        rule( format(message, min, max), it -> Validators.rangeValue(it, min, max) );
    }

    /**
     * Validates that the value of the String is in the established range <br />
     * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
     * @param min minimum value
     * @param max maximum value
     */
    public void rangeValue(double min, double max) {
        rangeValue(min, max, messages.getRangeValueMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="minAge">
    /**
     * Validates that the period from the entered date to the current date is greater than or equal to a minimum age
     * <br/>
     * <b>Warning:</b> This function makes use of the current date of the device <br />
     * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String, String)} rule first.
     * @param format Describing the date and time format
     * @param age minimum age
     * @param message Error message
     */
    public void minAge(String format, int age, String message) {
        rule( format(message, age), it -> Validators.minAge(it, format, age) );
    }

    /**
     * Validates that the period from the entered date to the current date is greater than or equal to a minimum age
     * <br/>
     * <b>Warning:</b> This function makes use of the current date of the device <br />
     * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String)} rule first
     * @param format Describing the date and time format
     * @param age minimum age
     */
    public void minAge(String format, int age) {
        minAge(format, age, messages.getMinAgeMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="expirationDate">
    /**
     * Validates that the entered date has not expired <br/>
     * <b>Warning:</b> This function makes use of the current date of the device <br />
     * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String, String)} rule first
     * @param format Describing the date and time format
     * @param message Error message
     */
    public void expirationDate(String format, String message) {
        rule( message, it -> Validators.expirationDate(it, format) );
    }

    /**
     * Validates that the entered date has not expired <br/>
     * <b>Warning:</b> This function makes use of the current date of the device <br />
     * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String)} rule first
     * @param format Describing the date and time format
     */
    public void expirationDate(String format) {
        expirationDate(format, messages.getExpirationDateMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="mustContainMinimum">
    /**
     * Validates that the String contains at least a minimum number of characters included in the condition
     * @param min minimum value.
     * @param condition String with desired characters.
     * @param message Error message.
     */
    public void mustContainMinimum(int min, String condition, String message) {
        rule( format(message, min, condition), it -> Validators.mustContainMinimum(it, min, condition) );
    }

    /**
     * Validates that the String contains at least a minimum number of characters included in the condition
     * @param min minimum value.
     * @param condition String with desired characters.
     */
    public void mustContainMinimum(int min, String condition) {
        mustContainMinimum(min, condition, messages.getMustContainMinimumMessage());
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

//        /**
//         * Validate that the value passes at least one of the validators.
//         * @param message Error message.
//         * @param validators Validators.
//         * @return Builder
//         */
//        public Builder any(String message, Validate... validators) {
//            return rule(message, it -> Arrays.stream(validators).anyMatch( validate -> validate.invoke(it) ) );
//        }

        //<editor-fold default-state="collapsed" desc="LENGTH RULES">

        //<editor-fold default-state="collapsed" desc="required">
        /**
         * Validates that the String is different from null and empty
         * @param message Error message
         * @return Builder
         */
        public Builder required(String message) {
            return rule(message, Validators::required);
        }

        /**
         * Validates that the String is different from null and empty
         * @return Builder
         */
        public Builder required() {
            return required(messages.getRequireMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="length">
        /**
         * Validates that the String has an exact length of characters
         * @param condition character length
         * @param message Error message
         * @return Builder
         */
        public Builder length(int condition, String message) {
            return rule(format(message, condition), it -> Validators.length(it, condition) );
        }

        /**
         * Validates that the String has an exact length of characters
         * @param condition character length
         * @return Builder
         */
        public Builder length(int condition) {
            return length(condition, messages.getLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="minLength">
        /**
         * Validates that the length of the String is not less than the condition
         * @param condition Minimum character length
         * @param message Error message
         * @return Builder
         */
        public Builder minLength(int condition, String message) {
            return rule(format(message, condition), it -> Validators.minLength(it, condition) );
        }

        /**
         * Validates that the length of the String is not less than the condition
         * @param condition Minimum character length
         * @return Builder
         */
        public Builder minLength(int condition) {
            return minLength(condition, messages.getMinLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="maxLength">
        /**
         * Validates that the length of the String is not greater than the condition
         * @param condition maximum character length
         * @param message Error message
         * @return Builder
         */
        public Builder maxLength(int condition, String message) {
            return rule(format(message, condition), it -> Validators.maxLength(it, condition) );
        }

        /**
         * Validates that the length of the String is not greater than the condition
         * @param condition maximum character length
         * @return Builder
         */
        public Builder maxLength(int condition) {
            return maxLength(condition, messages.getMaxLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="rangeLength">
        /**
         * Validates that the length of the String is in the established range
         * @param min Minimum character length
         * @param max Maximum character length
         * @param message Error message
         * @return Builder
         */
        public Builder rangeLength(int min, int max, String message) {
            return rule(format(message, min, max), it -> Validators.rangeLength(it, min, max) );
        }

        /**
         * Validates that the length of the String is in the established range
         * @param min Minimum character length
         * @param max Maximum character length
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
         * Validates that the String matches the regular expression
         * @param condition Regular expression
         * @param message Error message
         * @return Builder
         */
        public Builder regExp(String condition, String message) {
            return rule(format(message, condition), it -> Validators.regExp(it, condition) );
        }

        /**
         * Validates that the String matches the regular expression
         * @param condition Regular expression
         * @return Builder
         */
        public Builder regExp(String condition) {
            return regExp(condition, messages.getRegExpMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="email">
        /**
         * Validates that the String has an email format
         * @param message Error message.
         * @return Builder
         */
        public Builder email(String message) {
            return rule(message, Validators::email);
        }

        /**
         * Validates that the String has an email format
         * @return Builder
         */
        public Builder email() {
            return email(messages.getEmailMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="number">
        /**
         * Validates that the String is a numeric format <br/>
         * <b>Note:</b> This includes so many integers, decimal, and negative values
         * @param message Error message
         * @return Builder
         */
        public Builder number(String message) {
            return rule(message, Validators::number);
        }

        /**
         * Validates that the String is a numeric format <br/>
         * <b>Note:</b> This includes so many integers, decimal, and negative values
         * @return Builder
         */
        public Builder number() {
            return number(messages.getNumberMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="link">
        /**
         * Validates that the String is a link format
         * @param message Error message
         * @return Builder
         */
        public Builder link(String message) {
            return rule(message, Validators::link);
        }

        /**
         * Validates that the String is a link format
         * @return Builder
         */
        public Builder link() {
            return link(messages.getLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="wwwLink">
        /**
         * Validates that the String is a link with www format
         * @param message Error message.
         * @return Builder
         */
        public Builder wwwLink(String message) {
            return rule(message, Validators::wwwLink);
        }

        /**
         * Validates that the String is a link with www format
         * @return Builder
         */
        public Builder wwwLink() {
            return wwwLink(messages.getWwwLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="httpLink">
        /**
         * Validates that the String is a link with http format
         * @param message Error message
         * @return Builder
         */
        public Builder httpLink(String message) {
            return rule(message, Validators::httpLink);
        }

        /**
         * Validates that the String is a link with http format
         * @return Builder
         */
        public Builder httpLink() {
            return httpLink(messages.getHttpLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="httpsLink">
        /**
         * Validates that the String is a link with https format
         * @param message Error message
         * @return Builder
         */
        public Builder httpsLink(String message) {
            return rule(message, Validators::httpsLink);
        }

        /**
         * Validates that the String is a link with https format
         * @return Builder
         */
        public Builder httpsLink() {
            return httpsLink(messages.getHttpsLinkMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="ip">
        /**
         * Validates that the String is an ip format
         * @param message Error message
         * @return Builder
         */
        public Builder ip(String message) {
            return rule(message, Validators::ip);
        }

        /**
         * Validates that the String is an ip format
         * @return Builder
         */
        public Builder ip() {
            return ip(messages.getIpMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="ipv4">
        /**
         * Validates that the String is an ipv4 format
         * @param message Error message
         * @return Builder
         */
        public Builder ipv4(String message) {
            return rule(message, Validators::ipv4);
        }

        /**
         * Validates that the String is an ipv4 format
         * @return Builder
         */
        public Builder ipv4() {
            return ipv4(messages.getIpv4Message());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="ipv6">
        /**
         * Validates that the String is an ipv6 format
         * @param message Error message
         * @return Builder
         */
        public Builder ipv6(String message) {
            return rule(message, Validators::ipv6);
        }

        /**
         * Validates that the String is an ipv6 format
         * @return Builder
         */
        public Builder ipv6() {
            return ipv6(messages.getIpv6Message());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="time">
        /**
         * Validates that the String is a time format
         * @param message Error message
         * @return Builder
         */
        public Builder time(String message) {
            return rule(message, Validators::time);
        }

        /**
         * Validates that the String is a time format
         * @return Builder
         */
        public Builder time() {
            return time(messages.getTimeMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="time12">
        /**
         * Validates that the String is a time with 12-hour format
         * @param message Error message
         * @return Builder
         */
        public Builder time12(String message) {
            return rule(message, Validators::time12);
        }

        /**
         * Validates that the String is a time with 12-hour format
         * @return Builder
         */
        public Builder time12() {
            return time12(messages.getTime12Message());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="time24">
        /**
         * Validates that the String is a time with 24-hour format
         * @param message Error message
         * @return Builder
         */
        public Builder time24(String message) {
            return rule(message, Validators::time24);
        }

        /**
         * Validates that the String is a time with 24-hour format
         * @return Builder
         */
        public Builder time24() {
            return time24(messages.getTime24Message());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="onlyLetters">
        /**
         * Validates that the String contains only letters <br />
         * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
         * {@link #shouldOnlyContain(String, String)} function
         * @param message Error message
         * @return Builder
         */
        public Builder onlyLetters(String message) {
            return rule(message, Validators::onlyLetters);
        }

        /**
         * Validates that the String contains only letters <br />
         * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
         * {@link #shouldOnlyContain(String)} function
         * @return Builder
         */
        public Builder onlyLetters() {
            return onlyLetters(messages.getOnlyLettersMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="onlyAlphanumeric">
        /**
         * Validates that the String contains only alphanumeric characters <br />
         * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
         * {@link #shouldOnlyContain(String, String)} function
         * @param message Error message
         * @return Builder
         */
        public Builder onlyAlphanumeric(String message) {
            return rule(message, Validators::onlyAlphanumeric);
        }

        /**
         * Validates that the String contains only alphanumeric characters <br />
         * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
         * {@link #shouldOnlyContain(String)} function
         * @return Builder
         */
        public Builder onlyAlphanumeric() {
            return onlyAlphanumeric(messages.getOnlyAlphanumericMessage());
        }
        //</editor-fold>

        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="numberPattern">
        /**
         * Validates that the String matches the pattern, replacing the x's with numbers <br/>
         * <b>Example:</b> For the pattern +xx (xxx) xxx-xx-xx, the following Strings are valid:
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
         * Validates that the String matches the pattern, replacing the x's with numbers <br/>
         * <b>Example:</b> For the pattern +xx (xxx) xxx-xx-xx, the following Strings are valid:
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
         * Validates that the String to evaluate matches the specified date format
         * @param format Describing the date and time format
         * @param message Error message
         * @return Builder
         */
        public Builder dateFormat(String format, String message) {
            return rule(format(message, format), it -> Validators.dateFormat(it, format) );
        }

        /**
         * Validates that the String to evaluate matches the specified date format
         * @param format Describing the date and time format
         * @return Builder
         */
        public Builder dateFormat(String format) {
            return dateFormat(format, messages.getDateFormatMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="name">
        /**
         * Validates that the String is a proper name <br/>
         * <b>Note:</b>
         * <ul>
         *     <li>Capitalization is ignored</li>
         *     <li>
         *         Only valid proper names in English. to evaluate names in other languages it is recommended to use the
         *         {@link #regExp(String, String)} function.
         *     </li>
         * <ul/>
         * @param message Error message.
         * @return Builder
         */
        public Builder name(String message) {
            return rule(message, Validators::name);
        }

        /**
         * Validates that the String is a proper name <br/>
         * <b>Note:</b>
         * <ul>
         *     <li>Capitalization is ignored</li>
         *     <li>
         *         Only valid proper names in English. to evaluate names in other languages it is recommended to use the
         *         {@link #regExp(String)} function.
         *     </li>
         * <ul/>
         * @return Builder
         */
        public Builder name() {
            return name(messages.getNameMessage());
        }
        //</editor-fold>

        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="CONTENT RULES">

        //<editor-fold default-state="collapsed" desc="shouldOnlyContain">
        /**
         * Validates that the String only contains characters included in the condition
         * @param condition String with allowed characters.
         * @param message  Error message.
         * @return Builder
         */
        public Builder shouldOnlyContain(String condition, String message) {
            return rule(format(message, condition), it -> Validators.shouldOnlyContain(it, condition));
        }

        /**
         * Validates that the String only contains characters included in the condition
         * @param condition String with allowed characters
         * @return Builder
         */
        public Builder shouldOnlyContain(String condition) {
            return shouldOnlyContain(condition, messages.getShouldOnlyContainMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="onlyNumbers">
        /**
         * Validates that the String to evaluate only contains numeric characters
         * @param message Error message
         * @return Builder
         */
        public Builder onlyNumbers(String message) {
            return rule(message, Validators::onlyNumbers);
        }

        /**
         * Validates that the String to evaluate only contains numeric characters
         * @return Builder
         */
        public Builder onlyNumbers() {
            return onlyNumbers(messages.getOnlyNumbersMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="notContain">
        /**
         * Validates that the String does not contain any characters included in the condition
         * @param condition String with invalid characters
         * @param message Error message
         * @return Builder
         */
        public Builder notContain(String condition, String message) {
            return rule(format(message, condition), it -> Validators.notContain(it, condition));
        }

        /**
         * Validates that the String does not contain any characters included in the condition
         * @param condition String with invalid characters
         * @return Builder
         */
        public Builder notContain(String condition) {
            return notContain(condition, messages.getNotContainMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="mustContainOne">
        /**
         * Validates that the String contains at least one character included in the condition
         * @param condition String with desired characters
         * @param message Error message
         * @return Builder
         */
        public Builder mustContainOne(String condition, String message) {
            return rule(format(message, condition), it -> Validators.mustContainOne(it, condition));
        }

        /**
         * Validates that the String contains at least one character included in the condition
         * @param condition String with desired characters
         * @return Builder
         */
        public Builder mustContainOne(String condition) {
            return mustContainOne(condition, messages.getMustContainOneMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="maxValue">
        /**
         * Validates that the value of the String is not greater than the condition <br />
         * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
         * @param condition maximum value
         * @param message Error message
         * @return Builder
         */
        public Builder maxValue(double condition, String message) {
            return rule(format(message, condition), it -> Validators.maxValue(it, condition) );
        }

        /**
         * Validates that the value of the String is not greater than the condition <br />
         * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
         * @param condition maximum value
         * @return Builder
         */
        public Builder maxValue(double condition) {
            return maxValue(condition, messages.getMaxValueMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="minValue">
        /**
         * Validates that the value of the String is not less than the condition <br />
         * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
         * @param condition minimum value.
         * @param message Error message.
         * @return Builder
         */
        public Builder minValue(double condition, String message) {
            return rule(format(message, condition), it -> Validators.minValue(it, condition) );
        }

        /**
         * Validates that the value of the String is not less than the condition <br />
         * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
         * @param condition minimum value.
         * @return Builder
         */
        public Builder minValue(double condition) {
            return minValue(condition, messages.getMinValueMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="rangeValue">
        /**
         * Validates that the value of the String is in the established range <br />
         * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
         * @param min minimum value
         * @param max maximum value
         * @param message Error message
         * @return Builder
         */
        public Builder rangeValue(double min, double max, String message) {
            return rule( format(message, min, max), it -> Validators.rangeValue(it, min, max) );
        }

        /**
         * Validates that the value of the String is in the established range <br />
         * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
         * @param min minimum value
         * @param max maximum value
         * @return Builder
         */
        public Builder rangeValue(double min, double max) {
            return rangeValue(min, max, messages.getRangeValueMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="minAge">
        /**
         * Validates that the period from the entered date to the current date is greater than or equal to a minimum age
         * <br/>
         * <b>Warning:</b> This function makes use of the current date of the device <br />
         * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String, String)} rule first.
         * @param format Describing the date and time format
         * @param age minimum age
         * @param message Error message
         * @return Builder
         */
        public Builder minAge(String format, int age, String message) {
            return rule( format(message, age), it -> Validators.minAge(it, format, age) );
        }

        /**
         * Validates that the period from the entered date to the current date is greater than or equal to a minimum age
         * <br/>
         * <b>Warning:</b> This function makes use of the current date of the device <br />
         * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String)} rule first.
         * @param format Describing the date and time format
         * @param age minimum age
         * @return Builder
         */
        public Builder minAge(String format, int age) {
            return minAge(format, age, messages.getMinAgeMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="expirationDate">
        /**
         * Validates that the entered date has not expired <br/>
         * <b>Warning:</b> This function makes use of the current date of the device <br />
         * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String, String)} rule first
         * @param format Describing the date and time format
         * @param message Error message
         * @return Builder
         */
        public Builder expirationDate(String format, String message) {
            return rule( message, it -> Validators.expirationDate(it, format) );
        }

        /**
         * Validates that the entered date has not expired <br/>
         * <b>Warning:</b> This function makes use of the current date of the device <br />
         * <b>Note:</b> It is recommended to implement the {@link #dateFormat(String)} rule first
         * @param format Describing the date and time format
         * @return Builder
         */
        public Builder expirationDate(String format) {
            return expirationDate(format, messages.getExpirationDateMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="mustContainMinimum">
        /**
         * Validates that the String contains at least a minimum number of characters included in the condition
         * @param min minimum value
         * @param condition String with desired characters
         * @param message Error message
         * @return Builder
         */
        public Builder mustContainMinimum(int min, String condition, String message) {
            return rule( format(message, min, condition), it -> Validators.mustContainMinimum(it, min, condition) );
        }

        /**
         * Validates that the String contains at least a minimum number of characters included in the condition
         * @param min minimum value
         * @param condition String with desired characters
         * @return Builder
         */
        public Builder mustContainMinimum(int min, String condition) {
            return mustContainMinimum(min, condition, messages.getMustContainMinimumMessage());
        }
        //</editor-fold>

        //</editor-fold>

        //</editor-fold>

        /**
         * Event that is invoked when some rule is not fulfilled
         * @param onInvalidEvaluation Function with the error message
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