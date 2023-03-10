package com.apamatesoft.validator.messages;

public class MessagesEn implements Messages {

    @Override
    public String getNotMatchMessage() {
        return "Not match";
    }

    @Override
    public String getRequireMessage() {
        return "Required";
    }

    @Override
    public String getLengthMessage() {
        return "It requires %d characters";
    }

    @Override
    public String getMinLengthMessage() {
        return "It requires at least %d characters";
    }

    @Override
    public String getMaxLengthMessage() {
        return "It requires less than %d characters";
    }

    @Override
    public String getRangeLengthMessage() {
        return "The text must contain between %d to %d characters";
    }

    @Override
    public String getEmailMessage() {
        return "Email invalid";
    }

    @Override
    public String getNumberMessage() {
        return "It is not a number";
    }

    @Override
    public String getLinkMessage() {
        return "It is not a link";
    }

    @Override
    public String getWwwLinkMessage() {
        return "It is not a www link";
    }

    @Override
    public String getHttpLinkMessage() {
        return "It is not a http link";
    }

    @Override
    public String getHttpsLinkMessage() {
        return "It is not a https link";
    }

    @Override
    public String getIpMessage() {
        return "It is not a ip";
    }

    @Override
    public String getIpv4Message() {
        return "It is not a ipv4";
    }

    @Override
    public String getIpv6Message() {
        return "It is not a ipv6";
    }

    @Override
    public String getTimeMessage() {
        return "It is not a time";
    }

    @Override
    public String getTime12Message() {
        return "It is not a 12h format time";
    }

    @Override
    public String getTime24Message() {
        return "It is not a 24h format time";
    }

    @Override
    public String getShouldOnlyContainMessage() {
        return "They are just admitted the following characters %s";
    }

    @Override
    public String getOnlyNumbersMessage() {
        return "Just numbers";
    }

    @Override
    public String getOnlyCharactersMessage() {
        return "Just characters";
    }

    @Override
    public String getOnlyAlphanumericMessage() {
        return "Just alphanumeric characters";
    }

    @Override
    public String getNotContainMessage() {
        return "The following characters aren't admitted %s";
    }

    @Override
    public String getMustContainOneMessage() {
        return "At least one of the following characters is required: %s";
    }

    @Override
    public String getMaxValueMessage() {
        return "The value cannot be greater than %1$.2f";
    }

    @Override
    public String getRangeValueMessage() {
        return "The value must be between %1$.2f and %1$.2f";
    }

    @Override
    public String getMinValueMessage() {
        return "The value cannot be less than %1$.2f";
    }

    @Override
    public String getRegExpMessage() {
        return "Value does not match pattern %s";
    }

    @Override
    public String getNumberPatternMessage() {
        return "Does not match pattern %s";
    }

    @Override
    public String getDateFormatMessage() {
        return "It does not match the specified date format %s.";
    }

    @Override
    public String getMinAgeMessage() {
        return "you must be at least %d years old";
    }

    @Override
    public String getExpirationDateMessage() {
        return "expired date";
    }

    @Override
    public String getMustContainMinimumMessage() {
        return "must contain at least %d characters matching the following expression %s";
    }

}