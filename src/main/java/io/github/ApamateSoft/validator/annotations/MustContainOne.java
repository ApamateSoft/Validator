package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.*;

/**
 * Validates that the String contains at least one character included in the condition
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Repeatable(MustContainOneContainer.class)
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
