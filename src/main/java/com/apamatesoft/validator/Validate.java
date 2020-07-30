package com.apamatesoft.validator;

@FunctionalInterface
public interface Validate {
    boolean invoke(String evaluate);
}
