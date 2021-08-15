package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Constants.OCT;
import static com.apamatesoft.validator.constants.Validators.shouldOnlyContain;
import static org.junit.jupiter.api.Assertions.*;

public class ShouldOnlyContainTest {

    private static final String condition = OCT;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "012345678", "/*" };
        for (String string : strings) {
            if (shouldOnlyContain(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "01234567", "00" };
        for (String string : strings) {
            if (!shouldOnlyContain(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
