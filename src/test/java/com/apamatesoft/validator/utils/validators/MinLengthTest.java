package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Validators.minLength;
import static org.junit.jupiter.api.Assertions.*;

public class MinLengthTest {

    final int condition = 3;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "1", "12" };
        for (String string : strings) {
            if (minLength(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "123", "1234" };
        for (String string : strings) {
            if (!minLength(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
