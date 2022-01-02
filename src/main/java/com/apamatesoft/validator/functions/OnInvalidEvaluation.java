package com.apamatesoft.validator.functions;

@FunctionalInterface
public interface OnInvalidEvaluation {
    void invoke(String message);
}
