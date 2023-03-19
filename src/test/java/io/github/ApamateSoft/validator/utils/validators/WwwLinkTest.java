package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class WwwLinkTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "google.com", "http://google.com", "https://google.com" };
        for (String string : strings) {
            if (Validators.wwwLink(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "www.google.com", "www.google.com/api/auth?name=Name&lastName=LastName" };
        for (String string : strings) {
            if (!Validators.wwwLink(string)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
