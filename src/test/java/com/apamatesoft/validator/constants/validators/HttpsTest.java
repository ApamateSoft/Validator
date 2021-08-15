package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.httpsLink;
import static org.junit.jupiter.api.Assertions.*;

public class HttpsTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "google.com", "http.google.com", "https.google.com", "www.google.com", "http://google.com" };
        for (String string : strings) {
            if (httpsLink(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "https://google.com", "https://google.com/api/auth?name=Name&lastName=LastName" };
        for (String string : strings) {
            if (!httpsLink(string)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
