package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String matches the pattern, replacing the x's with numbers <br/>
 * <b>Example:</b> For the pattern +xx (xxx) xxx-xx-xx, the following Strings are valid:
 * <ul>
 *     <li>+12 (345) 678-90-12</li>
 *     <li>+xx (345) 678-90-12</li>
 *     <li>+xx (xxx) xxx-xx-xx</li>
 * <ul/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NumberPattern {
    /**
     * @return String with the pattern
     */
    String patter();

    /**
     *
     * @return Error message
     */
    String message() default "";
}