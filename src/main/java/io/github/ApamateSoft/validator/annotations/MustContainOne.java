package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String contains at least one character included in the condition
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MustContainOne {
    /**
     * @return String with desired characters
     */
    String condition();

    /**
     * @return Error message
     */
    String message() default "";
}
