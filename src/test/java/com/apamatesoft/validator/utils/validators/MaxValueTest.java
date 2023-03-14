package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Validators.maxValue;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class MaxValueTest {

    private static final String[] NOT_PERMIT = { null, "", "text", "2.51", "30", "91", "1,2", "2.6",  };
    private static final String[] PERMIT = { "2.5", "0.0", "-30", "2.49" };
    private static final double CONDITION = 2.5;

    @Test
    void notPermit() {
        assertFalse( stream(NOT_PERMIT).anyMatch( it -> maxValue(it, CONDITION) ) );
    }

    @Test
    void permit() {
        assertTrue( stream(PERMIT).allMatch( it -> maxValue(it, CONDITION) ) );
    }

}
