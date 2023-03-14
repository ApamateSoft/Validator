package io.github.ApamateSoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.utils.Validators.email;
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
