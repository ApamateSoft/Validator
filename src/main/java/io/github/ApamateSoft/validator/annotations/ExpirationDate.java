package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the entered date has not expired <br/>
 * <b>Warning:</b> This annotation makes use of the current date of the device <br />
 * <b>Note:</b> It is recommended to implement the {@Date} annotation first
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExpirationDate {
    /**
     * @return Describing the date and time format
     */
    String format();

    /**
     * @return Error message
     */
    String message() default "";
}
