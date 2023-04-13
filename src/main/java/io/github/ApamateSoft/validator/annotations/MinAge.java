package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the period from the entered date to the current date is greater than or equal to a minimum age <br/>
 * <b>Warning:</b> This annotation makes use of the current date of the device <br />
 * <b>Note:</b> It is recommended to implement the {@Date} annotation first
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MinAge {

    /**
     * @return Describing the date and time format
     */
    String format();

    /**
     * @return Minimum age
     */
    int age();

    /**
     * @return Error message
     */
    String message() default "";
}