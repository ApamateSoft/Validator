package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String contains only alphanumeric characters <br />
 * <b>Note:</b> It only recognizes letters of the English alphabet, for other alphabets it is recommended to use the
 * {@ShouldOnlyContain} annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface OnlyAlphanumeric {
    /**
     * @return Error message
     */
    String message() default "";
}
