package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Constants.OCT;
import static com.apamatesoft.validator.utils.Validators.mustContainOne;
import static org.junit.jupiter.api.Assertions.*;

public class MustContainOneTest {

    private static final String condition = OCT;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "@nick", "@nick89" };
        for (String string : strings) {
            if (mustContainOne(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "0", "@nick1", "91" };
        for (String string : strings) {
            if (!mustContainOne(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
