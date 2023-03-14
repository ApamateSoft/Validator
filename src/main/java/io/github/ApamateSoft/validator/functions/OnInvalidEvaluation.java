package io.github.ApamateSoft.validator.functions;

@FunctionalInterface
public interface OnInvalidEvaluation {
    void invoke(String message);
}
