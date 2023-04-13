package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the length of the String is not less than the condition
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinLength {

    /**
     * @return Minimum character length
     */
    int min();

    /**
     * @return Error message
     */
    String message() default "";
}
