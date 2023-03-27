package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Compare the value of this String with the value of the variable that matches the name set in the compareWith
 * attribute
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Compare {
    /**
     * @return Name of the variable, with which the value is compared
     */
    String compareWith();
    /**
     * @return Error message
     */
    String message() default "";
}
