package com.apamatesoft.validator.messages;

public class MessagesEs implements Messages {

    @Override
    public String getNotMatchMessage() {
        return "No coinciden";
    }

    @Override
    public String getRequireMessage() {
        return "Requerido";
    }

    @Override
    public String getLengthMessage() {
        return "Se requiere %d caracteres";
    }

    @Override
    public String getMinLengthMessage() {
        return "Se requiere al menos %d caracteres";
    }

    @Override
    public String getMaxLengthMessage() {
        return "Se requiere menos de %d caracteres";
    }

    @Override
    public String getEmailMessage() {
        return "Email invalido";
    }

    @Override
    public String getNumberMessage() {
        return "No es un número";
    }

    @Override
    public String getLinkMessage() {
        return "No es un enlace";
    }

    @Override
    public String getWwwLinkMessage() {
        return "No es un enlace www";
    }

    @Override
    public String getHttpLinkMessage() {
        return "No es un enlace http";
    }

    @Override
    public String getHttpsLinkMessage() {
        return "No es un enlace https";
    }

    @Override
    public String getIpMessage() {
        return "No es una ip";
    }

    @Override
    public String getIpv4Message() {
        return "No es una ipv4";
    }

    @Override
    public String getIpv6Message() {
        return "No es una ipv6";
    }

    @Override
    public String getTimeMessage() {
        return "No es una hora";
    }

    @Override
    public String getTime12Message() {
        return "No es una hora en formato de 12h";
    }

    @Override
    public String getTime24Message() {
        return "No es una hora en formato de 24h";
    }

    @Override
    public String getShouldOnlyContainMessage() {
        return "Solo se admiten los siguientes caracteres %s";
    }

    @Override
    public String getOnlyNumbersMessage() {
        return "Solo números";
    }

    @Override
    public String getOnlyCharactersMessage() {
        return "Solo caracteres";
    }

    @Override
    public String getOnlyAlphanumericMessage() {
        return "Solo caracteres alfanuméricos";
    }

    @Override
    public String getNotContainMessage() {
        return "No se admiten los siguientes caracteres %s";
    }

    @Override
    public String getMustContainOneMessage() {
        return "Se requiere al menos uno de los siguientes caracteres: %s";
    }

    @Override
    public String getMaxValueMessage() {
        return "El valor no puede ser mayor a %1$.2f";
    }

    @Override
    public String getMinValueMessage() {
        return "El valor no puede ser menor a %1$.2f";
    }

    @Override
    public String getRegExpMessage() {
        return "El valor no coincide con el patrón %s";
    }

    @Override
    public String getNumberPatternMessage() {
        return "No coincide con el patrón %s";
    }

    @Override
    public String getDateFormatMessage() {
        return "No coincide con el formato de fecha especificado %s.";
    }

}
