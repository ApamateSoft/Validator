package io.github.ApamateSoft.validator.exceptions;

public class InvalidEvaluationException extends Exception {

    private final String key;
    private final String value;

    public InvalidEvaluationException(String key, String value, String message) {
        super(message);
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

}
