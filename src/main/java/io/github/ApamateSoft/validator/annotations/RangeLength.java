package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the length of the String is in the established range
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RangeLength {
    /**
     * @return Minimum character length
     */
    int min();

    /**
     * @return Maximum character length
     */
    int max();

    /**
     * @return Error message
     */
    String message() default "";
}
