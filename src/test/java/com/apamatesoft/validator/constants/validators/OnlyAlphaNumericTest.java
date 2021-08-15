package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Constants.ALPHA_NUMERIC;
import static com.apamatesoft.validator.constants.Validators.onlyAlphaNumeric;
import static org.junit.jupiter.api.Assertions.*;

public class OnlyAlphaNumericTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "-", "a*", ">text", "a-", "-1.61", "$10,320.00" };
        for (String string : strings) {
            if (onlyAlphaNumeric(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        assertTrue(onlyAlphaNumeric(ALPHA_NUMERIC));
    }

}
