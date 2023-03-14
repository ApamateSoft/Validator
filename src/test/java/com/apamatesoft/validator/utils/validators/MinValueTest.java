package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Validators.minValue;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class MinValueTest {

    private static final String[] NOT_PERMIT = { null, "", "text", "2,5", "2.49", "0", "-2.5" };
    private static final String[] PERMIT = { "2.5", "2.51", "30" };
    private static final double CONDITION = 2.5;

    @Test
    void notPermit() {
        assertFalse( stream(NOT_PERMIT).anyMatch( it -> minValue(it, CONDITION) ) );
    }

    @Test
    void permit() {
        assertTrue( stream(PERMIT).allMatch( it -> minValue(it, CONDITION) ) );
    }

}
