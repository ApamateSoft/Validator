package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Validates that the String is a proper name <br/>
 * <b>Note:</b>
 * <ul>
 *     <li>Capitalization is ignored</li>
 *     <li>
 *         Only valid proper names in English. to evaluate names in other languages it is recommended to use the
 *         {@RegExp()} annotation
 *     </li>
 * <ul/>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Name {
    /**
     * @return Error message
     */
    String message() default "";
}