package com.apamatesoft.validator.functions;

@FunctionalInterface
public interface Validate {
    boolean invoke(String evaluate);
}
