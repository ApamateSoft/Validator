package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Validators.length;
import static org.junit.jupiter.api.Assertions.*;

public class LengthTest {

    final int condition = 3;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "12", "1234" };
        for (String string : strings) {
            if (length(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        assertTrue(length("123", condition));
    }

}