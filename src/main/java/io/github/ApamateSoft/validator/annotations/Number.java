package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String is a numeric format <br/>
 * <b>Note:</b> This includes so many integers, decimal, and negative values.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Number {
    /**
     * @return Error message
     */
    String message() default "";
}
