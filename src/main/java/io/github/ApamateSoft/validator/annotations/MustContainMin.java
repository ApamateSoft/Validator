package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String contains at least a minimum number of characters included in the condition
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MustContainMin {
    /**
     * @return Minimum value
     */
    int min();

    /**
     * @return String with desired characters
     */
    String condition();

    /**
     * @return Error message
     */
    String message() default "";
}
