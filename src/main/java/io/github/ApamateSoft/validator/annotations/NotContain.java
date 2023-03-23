package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String does not contain any characters included in the condition
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotContain {
    /**
     * @return String with invalid characters
     */
    String condition();

    /**
     * @return Error message
     */
    String message() default "";
}
