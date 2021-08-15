package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;
import static com.apamatesoft.validator.constants.Validators.wwwLink;
import static org.junit.jupiter.api.Assertions.*;

public class WwwLinkTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "google.com", "http://google.com", "https://google.com" };
        for (String string : strings) {
            if (wwwLink(string)) {
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
            if (!wwwLink(string)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
