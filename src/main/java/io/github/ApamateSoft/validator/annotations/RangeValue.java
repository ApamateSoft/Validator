package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the value of the String is in the established range <br />
 * <b>Note:</b> It is recommended to implement the {@Number} annotation first
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface RangeValue {
    /**
     * @return Minimum value
     */
    double min();

    /**
     * @return Maximum value
     */
    double max();

    /**
     * @return Error message
     */
    String message() default "";
}
