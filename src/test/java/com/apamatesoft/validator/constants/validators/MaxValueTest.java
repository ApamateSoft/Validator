package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.maxValue;
import static org.junit.jupiter.api.Assertions.*;

public class MaxValueTest {

    private static final double condition = 2.5;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "2.5", "1,2", "-2.6", "text" };
        for (String string : strings) {
            if (maxValue(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "2.51", "30", "91" };
        for (String string : strings) {
            if (!maxValue(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
