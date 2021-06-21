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
    public String getNumericFormat() {
        return "No es un número";
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
    public String getNotContainMessage() {
        return "No se admiten los siguientes caracteres %s";
    }

    @Override
    public String getMustContainOneMessage() {
        return "Se requiere al menos uno de los siguientes caracteres: %s";
    }

    @Override
    public String getMaxMessage() {
        return "El valor no puede ser mayor a %d";
    }

    @Override
    public String getMinMessage() {
        return "El valor no puede ser menor a %d";
    }

    @Override
    public String getEqualMessage() {
        return "El valor debe ser igual a %d";
    }

}
