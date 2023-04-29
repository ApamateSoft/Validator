package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.annotations.*;
import io.github.ApamateSoft.validator.annotations.Number;
import io.github.ApamateSoft.validator.utils.Validators;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.messages.Messages;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import io.github.ApamateSoft.validator.functions.Validate;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;

/**
 * <h1>Validator</h1>
 *
 * Facilitates the validation of Strings by chaining a series of rules
 *
 * @author ApamateSoft
 * @version 1.3.2
 */
public class Validator implements Cloneable {

    private static Messages messages = new MessagesEn();

    private final List<Rule> rules = new ArrayList<>();
    private OnInvalidEvaluation onInvalidEvaluation;
    private String notMatchMessage = messages.getCompareMessage();

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

    public static void validOrFail(Object obj) throws InvalidEvaluationException {
        for (Field field : obj.getClass().getDeclaredFields()) {

            if (!field.getType().equals(String.class)) continue;

            for (Annotation annotation : field.getDeclaredAnnotations()) {

                try {
                    field.setAccessible(true);
                    String value = (String) field.get(obj);
                    String fieldName = field.getName();

                    if (annotation instanceof Compare) {
                        Compare compareAnnotation = (Compare) annotation;
                        Field f = stream(obj.getClass().getDeclaredFields())
                            .filter( it -> it.getName().equals(compareAnnotation.compareWith()) )
                            .findFirst()
                            .orElseThrow( () ->
                                new InvalidEvaluationException(
                                    fieldName,
                                    value,
                                    compareAnnotation.message().isEmpty() ? messages.getCompareMessage() : compareAnnotation.message()
                                )
                            );

                        f.setAccessible(true);
                        String compare = (String) f.get(obj);
                        if (compare==null || compare.isEmpty() || !value.equals(compare))
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                compareAnnotation.message().isEmpty() ? messages.getCompareMessage() : compareAnnotation.message()
                            );
                    }

                    if (annotation instanceof Required) {
                        if (!Validators.required(value)) {
                            Required required = (Required) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                required.message().isEmpty() ? messages.getRequiredMessage() : required.message()
                            );
                        }
                    }

                    if (annotation instanceof Length) {
                        Length length = (Length) annotation;
                        if (!Validators.length(value, length.length())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(length.message().isEmpty() ? messages.getLengthMessage() : length.message(), length.length())
                            );
                        }
                    }

                    if (annotation instanceof MinLength) {
                        MinLength minLength = (MinLength) annotation;
                        if (!Validators.minLength(value, minLength.min())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(minLength.message().isEmpty() ? messages.getMinLengthMessage() : minLength.message(), minLength.min())
                            );
                        }
                    }

                    if (annotation instanceof MaxLength) {
                        MaxLength maxLength = (MaxLength) annotation;
                        if (!Validators.maxLength(value, maxLength.max())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(maxLength.message().isEmpty() ? messages.getMaxLengthMessage() : maxLength.message(), maxLength.max())
                            );
                        }
                    }

                    if (annotation instanceof RangeLength) {
                        RangeLength rangeLength = (RangeLength) annotation;
                        if (!Validators.rangeLength(value, rangeLength.min(), rangeLength.max())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(rangeLength.message().isEmpty() ? messages.getRangeLengthMessage() : rangeLength.message(), rangeLength.min(), rangeLength.max())
                            );
                        }
                    }

                    if (annotation instanceof RegExp) {
                        RegExp regExp = (RegExp) annotation;
                        if (!Validators.regExp(value, regExp.regExp())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(regExp.message().isEmpty() ? messages.getRegExpMessage() : regExp.message(), regExp.regExp())
                            );
                        }
                    }

                    if (annotation instanceof Email) {
                        if (!Validators.email(value)) {
                            Email email = (Email) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                email.message().isEmpty() ? messages.getEmailMessage() : email.message()
                            );
                        }
                    }

                    if (annotation instanceof Number) {
                        if (!Validators.number(value)) {
                            Number number = (Number) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                number.message().isEmpty() ? messages.getNumberMessage() : number.message()
                            );
                        }
                    }

                    if (annotation instanceof Link) {
                        if (!Validators.link(value)) {
                            Link link = (Link) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                link.message().isEmpty() ? messages.getLinkMessage() : link.message()
                            );
                        }
                    }

                    if (annotation instanceof WwwLink) {
                        if (!Validators.wwwLink(value)) {
                            WwwLink wwwLink = (WwwLink) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                wwwLink.message().isEmpty() ? messages.getWwwLinkMessage() : wwwLink.message()
                            );
                        }
                    }

                    if (annotation instanceof HttpLink) {
                        if (!Validators.httpLink(value)) {
                            HttpLink httpLink = (HttpLink) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                httpLink.message().isEmpty() ? messages.getHttpLinkMessage() : httpLink.message()
                            );
                        }
                    }

                    if (annotation instanceof HttpsLink) {
                        if (!Validators.httpsLink(value)) {
                            HttpsLink httpsLink = (HttpsLink) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                httpsLink.message().isEmpty() ? messages.getHttpsLinkMessage() : httpsLink.message()
                            );
                        }
                    }

                    if (annotation instanceof Ip) {
                        if (!Validators.ip(value)) {
                            Ip ip = (Ip) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                ip.message().isEmpty() ? messages.getIpMessage() : ip.message()
                            );
                        }
                    }

                    if (annotation instanceof Ipv4) {
                        if (!Validators.ipv4(value)) {
                            Ipv4 ipv4 = (Ipv4) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                ipv4.message().isEmpty() ? messages.getIpv4Message() : ipv4.message()
                            );
                        }
                    }

                    if (annotation instanceof Ipv6) {
                        if (!Validators.ipv6(value)) {
                            Ipv6 ipv6 = (Ipv6) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                ipv6.message().isEmpty() ? messages.getIpv6Message() : ipv6.message()
                            );
                        }
                    }

                    if (annotation instanceof Time) {
                        if (!Validators.time(value)) {
                            Time time = (Time) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                time.message().isEmpty() ? messages.getTimeMessage() : time.message()
                            );
                        }
                    }

                    if (annotation instanceof Time12) {
                        if (!Validators.time12(value)) {
                            Time12 time12 = (Time12) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                time12.message().isEmpty() ? messages.getTime12Message() : time12.message()
                            );
                        }
                    }

                    if (annotation instanceof Time24) {
                        if (!Validators.time24(value)) {
                            Time24 time24 = (Time24) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                time24.message().isEmpty() ? messages.getTime24Message() : time24.message()
                            );
                        }
                    }

                    if (annotation instanceof NumberPattern) {
                        NumberPattern numberPattern = (NumberPattern) annotation;
                        if (!Validators.numberPattern(value, numberPattern.patter())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(numberPattern.message().isEmpty() ? messages.getNumberPatternMessage() : numberPattern.message(), numberPattern.patter())
                            );
                        }
                    }

                    if (annotation instanceof Date) {
                        Date date = (Date) annotation;
                        if (!Validators.date(value, date.format())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(date.message().isEmpty() ? messages.getDateMessage() : date.message(), date.format())
                            );
                        }
                    }

                    if (annotation instanceof Name) {
                        if (!Validators.name(value)) {
                            Name name = (Name) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                name.message().isEmpty() ? messages.getNameMessage() : name.message()
                            );
                        }
                    }

                    if (annotation instanceof ShouldOnlyContain) {
                        ShouldOnlyContain shouldOnlyContain = (ShouldOnlyContain) annotation;
                        if (!Validators.shouldOnlyContain(value, shouldOnlyContain.alphabet())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(shouldOnlyContain.message().isEmpty() ? messages.getShouldOnlyContainMessage() : shouldOnlyContain.message(), shouldOnlyContain.alphabet())
                            );
                        }
                    }

                    if (annotation instanceof OnlyNumbers) {
                        if (!Validators.onlyNumbers(value)) {
                            OnlyNumbers onlyNumbers = (OnlyNumbers) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                onlyNumbers.message().isEmpty() ? messages.getOnlyNumbersMessage() : onlyNumbers.message()
                            );
                        }
                    }

                    if (annotation instanceof OnlyLetters) {
                        if (!Validators.onlyLetters(value)) {
                            OnlyLetters onlyLetters = (OnlyLetters) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                onlyLetters.message().isEmpty() ? messages.getOnlyLettersMessage() : onlyLetters.message()
                            );
                        }
                    }

                    if (annotation instanceof OnlyAlphanumeric) {
                        if (!Validators.onlyAlphanumeric(value)) {
                            OnlyAlphanumeric onlyAlphanumeric = (OnlyAlphanumeric) annotation;
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                onlyAlphanumeric.message().isEmpty() ? messages.getOnlyAlphanumericMessage() : onlyAlphanumeric.message()
                            );
                        }
                    }

                    if (annotation instanceof NotContain) {
                        NotContain notContain = (NotContain) annotation;
                        if (!Validators.notContain(value, notContain.alphabet())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(notContain.message().isEmpty() ? messages.getNotContainMessage() : notContain.message(), notContain.alphabet())
                            );
                        }
                    }

                    if (annotation instanceof NotContainContainer) {
                        NotContainContainer container = (NotContainContainer) annotation;
                        for (NotContain notContain : container.value()) {
                            if (!Validators.notContain(value, notContain.alphabet())) {
                                throw new InvalidEvaluationException(
                                    fieldName,
                                    value,
                                    format(notContain.message().isEmpty() ? messages.getNotContainMessage() : notContain.message(), notContain.alphabet())
                                );
                            }
                        }
                    }

                    if (annotation instanceof MustContainOne) {
                        MustContainOne mustContainOne = (MustContainOne) annotation;
                        if (!Validators.mustContainOne(value, mustContainOne.alphabet())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(mustContainOne.message().isEmpty() ? messages.getMustContainOneMessage() : mustContainOne.message(), mustContainOne.alphabet())
                            );
                        }
                    }

                    if (annotation instanceof MustContainOneContainer) {
                        MustContainOneContainer container = (MustContainOneContainer) annotation;
                        for (MustContainOne mustContainOne : container.value()) {
                            if (!Validators.mustContainOne(value, mustContainOne.alphabet())) {
                                throw new InvalidEvaluationException(
                                    fieldName,
                                    value,
                                    format(mustContainOne.message().isEmpty() ? messages.getMustContainOneMessage() : mustContainOne.message(), mustContainOne.alphabet())
                                );
                            }
                        }
                    }

                    if (annotation instanceof MaxValue) {
                        MaxValue maxValue = (MaxValue) annotation;
                        if (!Validators.maxValue(value, maxValue.max())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(maxValue.message().isEmpty() ? messages.getMaxValueMessage() : maxValue.message(), maxValue.max())
                            );
                        }
                    }

                    if (annotation instanceof MinValue) {
                        MinValue minValue = (MinValue) annotation;
                        if (!Validators.minValue(value, minValue.min())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(minValue.message().isEmpty() ? messages.getMinValueMessage() : minValue.message(), minValue.min())
                            );
                        }
                    }

                    if (annotation instanceof RangeValue) {
                        RangeValue rangeValue = (RangeValue) annotation;
                        if (!Validators.rangeValue(value, rangeValue.min(), rangeValue.max())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(rangeValue.message().isEmpty() ? messages.getRangeValueMessage() : rangeValue.message(), rangeValue.min(), rangeValue.max())
                            );
                        }
                    }

                    if (annotation instanceof MinAge) {
                        MinAge minAge = (MinAge) annotation;
                        if (!Validators.minAge(value, minAge.format(), minAge.age())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(minAge.message().isEmpty() ? messages.getMinAgeMessage() : minAge.message(), minAge.age())
                            );
                        }
                    }

                    if (annotation instanceof ExpirationDate) {
                        ExpirationDate expirationDate = (ExpirationDate) annotation;
                        if (!Validators.expirationDate(value, expirationDate.format())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(expirationDate.message().isEmpty() ? messages.getExpirationDateMessage() : expirationDate.message(), expirationDate.format())
                            );
                        }
                    }

                    if (annotation instanceof MustContainMin) {
                        MustContainMin mustContainMin = (MustContainMin) annotation;
                        if (!Validators.mustContainMin(value, mustContainMin.min(), mustContainMin.alphabet())) {
                            throw new InvalidEvaluationException(
                                fieldName,
                                value,
                                format(mustContainMin.message().isEmpty() ? messages.getMustContainMinMessage() : mustContainMin.message(), mustContainMin.min(), mustContainMin.alphabet())
                            );
                        }
                    }

                    if (annotation instanceof MustContainMinContainer) {
                        MustContainMinContainer container = (MustContainMinContainer) annotation;
                        for (MustContainMin mustContainMin : container.value()) {
                            if (!Validators.mustContainMin(value, mustContainMin.min(), mustContainMin.alphabet())) {
                                throw new InvalidEvaluationException(
                                    fieldName,
                                    value,
                                    format(mustContainMin.message().isEmpty() ? messages.getMustContainMinMessage() : mustContainMin.message(), mustContainMin.min(), mustContainMin.alphabet())
                                );
                            }
                        }
                    }

                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

            }

        }
    }

    /**
     * Validate that the String to evaluate meets all the rules <br>
     * <b>Note:</b> If the String does not meet any rule, the {@link #onInvalidEvaluation(OnInvalidEvaluation)} event will be invoked with the
     * corresponding error message
     * @param evaluate String to evaluate
     * @return true: if validation passes
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
     * Validate that the String to evaluate meets all the rules <br>
     * @param key Identifies the String to evaluate
     * @param evaluate String to evaluate
     * @throws InvalidEvaluationException Exception thrown if the String to evaluate does not meet any rule
     */
    public void validOrFail(String key, String evaluate) throws InvalidEvaluationException {
        for (Rule rule: rules)
            if (!rule.validate(evaluate))
                throw new InvalidEvaluationException(key, evaluate, rule.getMessage());
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
     * @param evaluate String to evaluate
     * @param compare String to compare
     * @return true: if validation passes
     */
    public boolean isMatch(String evaluate, String compare) {
        if (!evaluate.equals(compare)) {
            if (onInvalidEvaluation !=null) onInvalidEvaluation.invoke(notMatchMessage);
            return false;
        }
        return isValid(evaluate);
    }

    /**
     * Validate that both Strings match and that they meet all the rules
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
     * @param key Identifies the String to evaluate
     * @param evaluate String to evaluate.
     * @param compare String to compare.
     * @throws InvalidEvaluationException Exception thrown if the String to evaluate does not meet any rule.
     */
    public void compareOrFail(String key, String evaluate, String compare) throws InvalidEvaluationException {
        if (!evaluate.equals(compare))
            throw new InvalidEvaluationException(key, evaluate, notMatchMessage);
        validOrFail(key, evaluate);
    }

    /**
     * Sets the error message to display, in case the String comparison fails in the method
     * {@link #isMatch(String, String)}.
     * @param message Error message.
     */
    public void setNotMatchMessage(String message) {
        this.notMatchMessage = message;
    }

    //<editor-fold default-state="collapsed" desc="RULES">

    /**
     * Create a validation rule <br/>
     * <b>Example:<b/>
     * <pre>
     * new Validator.rule("Enter a text other than null", Objects::nonNull)
     * </pre>
     * @param message Error message
     * @param validate Function that returns true when the String to evaluate meets the conditions
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
        required(messages.getRequiredMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="length">
    /**
     * Validates that the String has an exact length of characters
     * @param length character length
     * @param message Error message
     */
    public void length(int length, String message) {
        rule(format(message, length), it -> Validators.length(it, length) );
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
     * Validates that the length of the String is not less than the min
     * @param min Minimum character length
     * @param message Error message
     */
    public void minLength(int min, String message) {
        rule(format(message, min), it -> Validators.minLength(it, min) );
    }

    /**
     * Validates that the length of the String is not less than the min
     * @param min Minimum character length
     */
    public void minLength(int min) {
        minLength(min, messages.getMinLengthMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="maxLength">
    /**
     * Validates that the length of the String is not greater than the max
     * @param max Maximum character length
     * @param message Error message
     */
    public void maxLength(int max, String message) {
        rule(format(message, max), it -> Validators.maxLength(it, max) );
    }

    /**
     * Validates that the length of the String is not greater than the condition
     * @param condition Maximum character length
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
     * @param regExp Regular expression
     * @param message Error message
     */
    public void regExp(String regExp, String message) {
        rule(format(message, regExp), it -> Validators.regExp(it, regExp) );
    }

    /**
     * Validates that the String matches the regular expression
     * @param regExp Regular expression
     */
    public void regExp(String regExp) {
        regExp(regExp, messages.getRegExpMessage());
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
     * Validate that the String is a time with 24 hours format
     * @param message Error message.
     */
    public void time24(String message) {
        rule(message, Validators::time24);
    }

    /**
     * Validate that the String is a time with 24 hours format
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

    //<editor-fold default-state="collapsed" desc="date">
    /**
     * Validates that the String to evaluate matches the specified date format
     * @param format Describing the date and time format
     * @param message Error message
     */
    public void date(String format, String message) {
        rule(format(message, format), it -> Validators.date(it, format) );
    }

    /**
     * Validates that the String to evaluate matches the specified date format
     * @param format Describing the date and time format
     */
    public void date(String format) {
        date(format, messages.getDateMessage());
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
     *         {@link #regExp(String, String)} function
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
     * Validates that the String only contains characters included in the alphabet
     * @param alphabet String with allowed characters
     * @param message  Error message
     */
    public void shouldOnlyContain(String alphabet, String message) {
        rule(format(message, alphabet), it -> Validators.shouldOnlyContain(it, alphabet) );
    }

    /**
     * Validates that the String only contains characters included in the alphabet
     * @param alphabet String with allowed characters
     */
    public void shouldOnlyContain(String alphabet) {
        shouldOnlyContain(alphabet, messages.getShouldOnlyContainMessage());
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
     * Validates that the String does not contain any characters included in the alphabet
     * @param alphabet String with invalid characters
     * @param message Error message
     */
    public void notContain(String alphabet, String message) {
        rule(format(message, alphabet), it -> Validators.notContain(it, alphabet) );
    }

    /**
     * Validates that the String does not contain any characters included in the alphabet
     * @param alphabet String with invalid characters
     */
    public void notContain(String alphabet) {
        notContain(alphabet, messages.getNotContainMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="mustContainOne">
    /**
     * Validates that the String contains at least one character included in the alphabet
     * @param alphabet String with desired characters
     * @param message Error message
     */
    public void mustContainOne(String alphabet, String message) {
        rule(format(message, alphabet), it -> Validators.mustContainOne(it, alphabet) );
    }

    /**
     * Validates that the String contains at least one character included in the alphabet
     * @param alphabet String with desired characters
     */
    public void mustContainOne(String alphabet) {
        mustContainOne(alphabet, messages.getMustContainOneMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="maxValue">
    /**
     * Validates that the value of the String is not greater than the max <br />
     * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
     * @param max Maximum value
     * @param message Error message
     */
    public void maxValue(double max, String message) {
        rule(format(message, max), it -> Validators.maxValue(it, max) );
    }

    /**
     * Validates that the value of the String is not greater than the max <br />
     * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
     * @param max Maximum value.
     */
    public void maxValue(double max) {
        maxValue(max, messages.getMaxValueMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="minValue">
    /**
     * Validates that the value of the String is not less than the condition <br />
     * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
     * @param condition Minimum value
     * @param message Error message
     */
    public void minValue(double condition, String message) {
        rule(format(message, condition), it -> Validators.minValue(it, condition) );
    }

    /**
     * Validates that the value of the String is not less than the condition <br />
     * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
     * @param condition Minimum value.
     */
    public void minValue(double condition) {
        minValue(condition, messages.getMinValueMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="rangeValue">

    /**
     * Validates that the value of the String is in the established range <br />
     * <b>Note:</b> It is recommended to implement the {@link #number(String)} rule first
     * @param min Minimum value
     * @param max Maximum value
     * @param message Error message
     */
    public void rangeValue(double min, double max, String message) {
        rule( format(message, min, max), it -> Validators.rangeValue(it, min, max) );
    }

    /**
     * Validates that the value of the String is in the established range <br />
     * <b>Note:</b> It is recommended to implement the {@link #number()} rule first
     * @param min Minimum value
     * @param max Maximum value
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
     * <b>Note:</b> It is recommended to implement the {@link #date(String, String)} rule first.
     * @param format Describing the date and time format
     * @param age Minimum age
     * @param message Error message
     */
    public void minAge(String format, int age, String message) {
        rule( format(message, age), it -> Validators.minAge(it, format, age) );
    }

    /**
     * Validates that the period from the entered date to the current date is greater than or equal to a minimum age
     * <br/>
     * <b>Warning:</b> This function makes use of the current date of the device <br />
     * <b>Note:</b> It is recommended to implement the {@link #date(String)} rule first
     * @param format Describing the date and time format
     * @param age Minimum age
     */
    public void minAge(String format, int age) {
        minAge(format, age, messages.getMinAgeMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="expirationDate">
    /**
     * Validates that the entered date has not expired <br/>
     * <b>Warning:</b> This function makes use of the current date of the device <br />
     * <b>Note:</b> It is recommended to implement the {@link #date(String, String)} rule first
     * @param format Describing the date and time format
     * @param message Error message
     */
    public void expirationDate(String format, String message) {
        rule( message, it -> Validators.expirationDate(it, format) );
    }

    /**
     * Validates that the entered date has not expired <br/>
     * <b>Warning:</b> This function makes use of the current date of the device <br />
     * <b>Note:</b> It is recommended to implement the {@link #date(String)} rule first
     * @param format Describing the date and time format
     */
    public void expirationDate(String format) {
        expirationDate(format, messages.getExpirationDateMessage());
    }
    //</editor-fold>

    //<editor-fold default-state="collapsed" desc="mustContainMin">
    /**
     * Validates that the String contains at least a minimum number of characters included in the alphabet
     * @param min Minimum value
     * @param alphabet String with desired characters
     * @param message Error message
     */
    public void mustContainMin(int min, String alphabet, String message) {
        rule( format(message, min, alphabet), it -> Validators.mustContainMin(it, min, alphabet) );
    }

    /**
     * Validates that the String contains at least a minimum number of characters included in the alphabet
     * @param min minimum value.
     * @param alphabet String with desired characters.
     */
    public void mustContainMin(int min, String alphabet) {
        mustContainMin(min, alphabet, messages.getMustContainMinMessage());
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
        private String notMatchMessage = messages.getCompareMessage();

        /**
         * Sets the error message to display, in case the String comparison fails in the method
         * {@link #isMatch(String, String)}.
         * @param message Error message.
         * @return Builder
         */
        public Builder setNotMatchMessage(String message) {
            this.notMatchMessage = message;
            return this;
        }

        //<editor-fold default-state="collapsed" desc="RULES">

        /**
         * Create a validation rule
         * <br/>
         * <b>Ejemplo:<b/>
         * <pre>
         * new Validator.rule("Enter a text other than null", Objects::nonNull)
         * </pre>
         * @param message Error message.
         * @param validate Function that returns true when the String to evaluate meets the conditions
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
            return required(messages.getRequiredMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="length">
        /**
         * Validates that the String has an exact length of characters
         * @param length character length
         * @param message Error message
         * @return Builder
         */
        public Builder length(int length, String message) {
            return rule(format(message, length), it -> Validators.length(it, length) );
        }

        /**
         * Validates that the String has an exact length of characters
         * @param length character length
         * @return Builder
         */
        public Builder length(int length) {
            return length(length, messages.getLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="minLength">
        /**
         * Validates that the length of the String is not less than the min
         * @param min Minimum character length
         * @param message Error message
         * @return Builder
         */
        public Builder minLength(int min, String message) {
            return rule(format(message, min), it -> Validators.minLength(it, min) );
        }

        /**
         * Validates that the length of the String is not less than the min
         * @param min Minimum character length
         * @return Builder
         */
        public Builder minLength(int min) {
            return minLength(min, messages.getMinLengthMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="maxLength">
        /**
         * Validates that the length of the String is not greater than the max
         * @param max Maximum character length
         * @param message Error message
         * @return Builder
         */
        public Builder maxLength(int max, String message) {
            return rule(format(message, max), it -> Validators.maxLength(it, max) );
        }

        /**
         * Validates that the length of the String is not greater than the max
         * @param max Maximum character length
         * @return Builder
         */
        public Builder maxLength(int max) {
            return maxLength(max, messages.getMaxLengthMessage());
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
         * @param regExp Regular expression
         * @param message Error message
         * @return Builder
         */
        public Builder regExp(String regExp, String message) {
            return rule(format(message, regExp), it -> Validators.regExp(it, regExp) );
        }

        /**
         * Validates that the String matches the regular expression
         * @param regExp Regular expression
         * @return Builder
         */
        public Builder regExp(String regExp) {
            return regExp(regExp, messages.getRegExpMessage());
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

        //<editor-fold default-state="collapsed" desc="date">
        /**
         * Validates that the String to evaluate matches the specified date format
         * @param format Describing the date and time format
         * @param message Error message
         * @return Builder
         */
        public Builder date(String format, String message) {
            return rule(format(message, format), it -> Validators.date(it, format) );
        }

        /**
         * Validates that the String to evaluate matches the specified date format
         * @param format Describing the date and time format
         * @return Builder
         */
        public Builder date(String format) {
            return date(format, messages.getDateMessage());
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
         * Validates that the String only contains characters included in the alphabet
         * @param alphabet String with allowed characters.
         * @param message  Error message.
         * @return Builder
         */
        public Builder shouldOnlyContain(String alphabet, String message) {
            return rule(format(message, alphabet), it -> Validators.shouldOnlyContain(it, alphabet));
        }

        /**
         * Validates that the String only contains characters included in the alphabet
         * @param alphabet String with allowed characters
         * @return Builder
         */
        public Builder shouldOnlyContain(String alphabet) {
            return shouldOnlyContain(alphabet, messages.getShouldOnlyContainMessage());
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
         * Validates that the String does not contain any characters included in the alphabet
         * @param alphabet String with invalid characters
         * @param message Error message
         * @return Builder
         */
        public Builder notContain(String alphabet, String message) {
            return rule(format(message, alphabet), it -> Validators.notContain(it, alphabet));
        }

        /**
         * Validates that the String does not contain any characters included in the alphabet
         * @param alphabet String with invalid characters
         * @return Builder
         */
        public Builder notContain(String alphabet) {
            return notContain(alphabet, messages.getNotContainMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="mustContainOne">
        /**
         * Validates that the String contains at least one character included in the alphabet
         * @param alphabet String with desired characters
         * @param message Error message
         * @return Builder
         */
        public Builder mustContainOne(String alphabet, String message) {
            return rule(format(message, alphabet), it -> Validators.mustContainOne(it, alphabet));
        }

        /**
         * Validates that the String contains at least one character included in the alphabet
         * @param alphabet String with desired characters
         * @return Builder
         */
        public Builder mustContainOne(String alphabet) {
            return mustContainOne(alphabet, messages.getMustContainOneMessage());
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
         * <b>Note:</b> It is recommended to implement the {@link #date(String, String)} rule first.
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
         * <b>Note:</b> It is recommended to implement the {@link #date(String)} rule first.
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
         * <b>Note:</b> It is recommended to implement the {@link #date(String, String)} rule first
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
         * <b>Note:</b> It is recommended to implement the {@link #date(String)} rule first
         * @param format Describing the date and time format
         * @return Builder
         */
        public Builder expirationDate(String format) {
            return expirationDate(format, messages.getExpirationDateMessage());
        }
        //</editor-fold>

        //<editor-fold default-state="collapsed" desc="mustContainMin">
        /**
         * Validates that the String contains at least a minimum number of characters included in the alphabet
         * @param min minimum value
         * @param alphabet String with desired characters
         * @param message Error message
         * @return Builder
         */
        public Builder mustContainMin(int min, String alphabet, String message) {
            return rule( format(message, min, alphabet), it -> Validators.mustContainMin(it, min, alphabet) );
        }

        /**
         * Validates that the String contains at least a minimum number of characters included in the alphabet
         * @param min minimum value
         * @param alphabet String with desired characters
         * @return Builder
         */
        public Builder mustContainMin(int min, String alphabet) {
            return mustContainMin(min, alphabet, messages.getMustContainMinMessage());
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