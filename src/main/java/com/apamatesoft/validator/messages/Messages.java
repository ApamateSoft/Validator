package com.apamatesoft.validator.messages;

public interface Messages {
    String getNotMatchMessage();
    String getRequireMessage();
    String getLengthMessage();
    String getMinLengthMessage();
    String getMaxLengthMessage();
    String getRegExpMessage();
    String getEmailMessage();
    String getNumberMessage();
    String getLinkMessage();
    String getWwwLinkMessage();
    String getHttpLinkMessage();
    String getHttpsLinkMessage();
    String getIpMessage();
    String getIpv4Message();
    String getIpv6Message();
    String getTimeMessage();
    String getTime12Message();
    String getTime24Message();
    String getOnlyNumbersMessage();
    String getOnlyCharactersMessage();
    String getOnlyAlphanumericMessage();
    String getShouldOnlyContainMessage();
    String getNotContainMessage();
    String getMustContainOneMessage();
    String getMinValueMessage();
    String getMaxValueMessage();
    String getRangeValueMessage();
    String getNumberPatternMessage();
    String getDateFormatMessage();
}
