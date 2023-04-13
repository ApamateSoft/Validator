package io.github.ApamateSoft.validator.messages;

public class MessagesEs implements Messages {

    @Override
    public String getDateMessage() {
        return "La fecha no coincide con el formato %s";
    }

    @Override
    public String getEmailMessage() {
        return "Correo electrónico invalido";
    }

    @Override
    public String getExpirationDateMessage() {
        return "Fecha expirada";
    }

    @Override
    public String getHttpLinkMessage() {
        return "Enlace http inválido";
    }

    @Override
    public String getHttpsLinkMessage() {
        return "Enlace https inválido";
    }

    @Override
    public String getIpMessage() {
        return "IP inválida";
    }

    @Override
    public String getIpv4Message() {
        return "IPv4 inválida";
    }

    @Override
    public String getIpv6Message() {
        return "IPv6 inválida";
    }

    @Override
    public String getLengthMessage() {
        return "Se requiere %d caracteres";
    }

    @Override
    public String getLinkMessage() {
        return "Enlace inválido";
    }

    @Override
    public String getMaxLengthMessage() {
        return "Se requiere %d o menos caracteres";
    }

    @Override
    public String getMaxValueMessage() {
        return "El valor no puede ser mayor a %1$.2f";
    }

    @Override
    public String getMinAgeMessage() {
        return "Se debe tener al menos %d años";
    }

    @Override
    public String getMinLengthMessage() {
        return "Se requiere %d o más caracteres";
    }

    @Override
    public String getMinValueMessage() {
        return "El valor no puede ser menor a %1$.2f";
    }

    @Override
    public String getMustContainMinMessage() {
        return "Se requiere al menos %d de los siguientes caracteres: %s";
    }

    @Override
    public String getMustContainOneMessage() {
        return "Se requiere al menos uno de los siguientes caracteres: %s";
    }

    @Override
    public String getNameMessage() {
        return "Debe introducir un nombre personal válido";
    }

    @Override
    public String getNotContainMessage() {
        return "No se admiten los siguientes caracteres %s";
    }

    @Override
    public String getCompareMessage() {
        return "No coinciden";
    }

    @Override
    public String getNumberMessage() {
        return "No es un número";
    }

    @Override
    public String getNumberPatternMessage() {
        return "No coincide con el patrón %s";
    }

    @Override
    public String getOnlyAlphanumericMessage() {
        return "Solo caracteres alfanuméricos";
    }

    @Override
    public String getOnlyLettersMessage() {
        return "Solo letras";
    }

    @Override
    public String getOnlyNumbersMessage() {
        return "Solo números";
    }

    @Override
    public String getRangeLengthMessage() {
        return "El texto debe contener entre %d a %d caracteres";
    }

    @Override
    public String getRangeValueMessage() {
        return "El valor debe estar entre %1$.2f y %1$.2f";
    }

    @Override
    public String getRegExpMessage() {
        return "El valor no coincide con la expresión regular %s";
    }

    @Override
    public String getRequiredMessage() {
        return "Requerido";
    }

    @Override
    public String getShouldOnlyContainMessage() {
        return "Solo se admiten los siguientes caracteres %s";
    }

    @Override
    public String getTimeMessage() {
        return "Hora inválida";
    }

    @Override
    public String getTime12Message() {
        return "Formato 12 horas invalido";
    }

    @Override
    public String getTime24Message() {
        return "Formato 24 horas invalido";
    }

    @Override
    public String getWwwLinkMessage() {
        return "Enlace www inválido";
    }

}
