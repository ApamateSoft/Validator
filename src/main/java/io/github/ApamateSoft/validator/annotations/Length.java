package io.github.ApamateSoft.validator.annotations;

public @interface Length {
    int length();
    String message() default "";
}
