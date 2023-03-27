package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String only contains characters included in the condition
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ShouldOnlyContain {
    /**
     * @return String with allowed characters
     */
    String condition();

    /**
     * @return Error message
     */
    String message() default "";
}
