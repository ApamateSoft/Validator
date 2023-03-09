package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Validators.number;
import static org.junit.jupiter.api.Assertions.*;

public class NumberTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "a1", "1a", "12345,6789", "123.456.789" };
        for (String string : strings) {
            if (number(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "123456789", "-123456789", "12345.6789", "-12345.6789", "1" };
        for (String string : strings) {
            if (!number(string)) {
                System.out.println(string);
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
