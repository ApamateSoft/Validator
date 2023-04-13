package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.*;

/**
 * Validates that the String contains at least a minimum number of characters included in the condition
 */
@Repeatable(MustContainMinContainer.class)
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
    String alphabet();

    /**
     * @return Error message
     */
    String message() default "";
}