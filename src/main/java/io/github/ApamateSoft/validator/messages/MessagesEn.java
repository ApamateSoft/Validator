package io.github.ApamateSoft.validator.messages;

public class MessagesEn implements Messages {

    @Override
    public String getDateMessage() {
        return "The date does not match the format %s";
    }

    @Override
    public String getEmailMessage() {
        return "Email invalid";
    }

    @Override
    public String getExpirationDateMessage() {
        return "Expired date";
    }

    @Override
    public String getHttpLinkMessage() {
        return "Invalid http link";
    }

    @Override
    public String getHttpsLinkMessage() {
        return "Invalid https link";
    }

    @Override
    public String getIpMessage() {
        return "Invalid IP";
    }

    @Override
    public String getIpv4Message() {
        return "Invalid IPv4";
    }

    @Override
    public String getIpv6Message() {
        return "Invalid IPv6";
    }

    @Override
    public String getLengthMessage() {
        return "It requires %d characters";
    }

    @Override
    public String getLinkMessage() {
        return "Invalid link";
    }

    @Override
    public String getMaxLengthMessage() {
        return "%d or less characters required";
    }

    @Override
    public String getMaxValueMessage() {
        return "The value cannot be greater than %1$.2f";
    }

    @Override
    public String getMinAgeMessage() {
        return "You must be at least %d years old";
    }

    @Override
    public String getMinLengthMessage() {
        return "%d or more characters are required";
    }

    @Override
    public String getMinValueMessage() {
        return "The value cannot be less than %1$.2f";
    }

    @Override
    public String getMustContainMinMessage() {
        return "At least %d of the following characters are required: %s";
    }

    @Override
    public String getMustContainOneMessage() {
        return "At least one of the following characters is required: %s";
    }

    @Override
    public String getNameMessage() {
        return "Debe introducir un nombre personal válido";
    }

    @Override
    public String getNotContainMessage() {
        return "The following characters aren't admitted %s";
    }

    @Override
    public String getCompareMessage() {
        return "Not match";
    }

    @Override
    public String getNumberMessage() {
        return "It is not a number";
    }

    @Override
    public String getNumberPatternMessage() {
        return "Does not match pattern %s";
    }

    @Override
    public String getOnlyAlphanumericMessage() {
        return "Just alphanumeric characters";
    }

    @Override
    public String getOnlyLettersMessage() {
        return "Only letters";
    }

    @Override
    public String getOnlyNumbersMessage() {
        return "Just numbers";
    }

    @Override
    public String getRangeLengthMessage() {
        return "The text must contain between %d to %d characters";
    }

    @Override
    public String getRangeValueMessage() {
        return "The value must be between %1$.2f and %1$.2f";
    }

    @Override
    public String getRegExpMessage() {
        return "The value does not match the regular expression %s";
    }

    @Override
    public String getRequiredMessage() {
        return "Required";
    }

    @Override
    public String getShouldOnlyContainMessage() {
        return "They are just admitted the following characters %s";
    }

    @Override
    public String getTimeMessage() {
        return "Time invalid";
    }

    @Override
    public String getTime12Message() {
        return "Invalid 12 hour format";
    }

    @Override
    public String getTime24Message() {
        return "Invalid 24 hour format";
    }

    @Override
    public String getWwwLinkMessage() {
        return "Invalid www link";
    }

}