package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;
import static com.apamatesoft.validator.constants.Validators.maxLength;
import static org.junit.jupiter.api.Assertions.*;

public class MaxLengthTest {

    final int condition = 3;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "1234" };
        for (String string : strings) {
            if (maxLength(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "1", "12", "123" };
        for (String string : strings) {
            if (!maxLength(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
