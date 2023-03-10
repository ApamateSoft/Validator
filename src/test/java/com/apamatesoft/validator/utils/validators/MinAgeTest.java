package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Validators.minAge;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MinAgeTest {

    private static final String[] NOT_PERMIT = {
        null,
        "",
        "example",
        "21091991",
        "21-09-1991",
        "1991/09/21",
        "09/21/1991",
        "21/09/3000"
    };
    private static final String[] PERMIT = {
        "21/09/1991"
    };
    private static final String FORMAT = "dd/MM/yyyy";
    private static final int AGE = 18;

    @Test
    void notPermit() {
        boolean b = stream(NOT_PERMIT).anyMatch( it -> minAge(it, FORMAT, AGE) );
        assertFalse(b);
    }

    @Test
    void permit() {
        boolean b = stream(PERMIT).allMatch( it -> minAge(it, FORMAT, AGE) );
        assertTrue(b);
    }

}
