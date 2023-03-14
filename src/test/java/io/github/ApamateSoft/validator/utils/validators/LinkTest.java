package io.github.ApamateSoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.utils.Validators.link;
import static org.junit.jupiter.api.Assertions.*;

public class LinkTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "google.com", "text", "a1", "1a", "12345,6789", "123.456.789" };
        for (String string : strings) {
            if (link(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "www.google.com", "http://google.com", "https://google.com", "http://google.com/api/auth?name=Name&lastName=LastName" };
        for (String string : strings) {
            if (!link(string)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
