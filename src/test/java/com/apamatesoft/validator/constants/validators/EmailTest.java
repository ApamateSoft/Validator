package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.email;
import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "example", "@mail", "example@mail", "example@mail.", "mail.com", "@mail.com" };
        for (String string : strings) {
            if (email(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "example@mail.com" };
        for (String string : strings) {
            if (!email(string)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
