package io.github.ApamateSoft.validator.annotations;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MustContainOneContainer {
    MustContainOne[] value();
}