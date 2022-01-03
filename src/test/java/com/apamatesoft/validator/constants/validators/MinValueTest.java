package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.minValue;
import static org.junit.jupiter.api.Assertions.*;

public class MinValueTest {

    private static final double condition = 2.5;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "2.5", "2.51", "2,00" };
        for (String string : strings) {
            if (minValue(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "2.49", "-1.01", "1", "0" };
        for (String string : strings) {
            if (!minValue(string, condition)) {
                System.out.println(string);
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
