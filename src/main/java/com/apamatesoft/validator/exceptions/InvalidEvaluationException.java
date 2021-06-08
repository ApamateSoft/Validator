package com.apamatesoft.validator.exceptions;

public class InvalidEvaluationException extends Exception {

    private final String value;

    public InvalidEvaluationException(String message, String value) {
        super(message);
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
