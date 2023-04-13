package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String matches the regular expression
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RegExp {
    /**
     * @return Regular expression
     */
    String regExp();

    /**
     * @return Error message
     */
    String message() default "";
}
