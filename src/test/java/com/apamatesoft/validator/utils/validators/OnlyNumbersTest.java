package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Validators.onlyNumbers;
import static org.junit.jupiter.api.Assertions.*;

public class OnlyNumbersTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "Name Lastname", "1a", "a1", "1a1", "a1a", "1.00", "1,00" };
        for (String string : strings) {
            if (onlyNumbers(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        assertTrue(onlyNumbers("123456789"));
    }

}