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
    public String getEmailMessage() {
        return "Email invalid";
    }

    @Override
    public String getNumericFormat() {
        return "It is not a number";
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
    public String getNotContainMessage() {
        return "The following characters aren't admitted %s";
    }

    @Override
    public String getMustContainOneMessage() {
        return "At least one of the following characters is required: %s";
    }

}
