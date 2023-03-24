package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.*;

/**
 * Validates that the String does not contain any characters included in the condition
 */
@Repeatable(NotContainContainer.class)
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
