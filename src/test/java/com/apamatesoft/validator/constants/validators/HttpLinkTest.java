package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.httpLink;
import static org.junit.jupiter.api.Assertions.*;

public class HttpLinkTest {

    @Test
    void notPermit() {
        final String[] strings = {  null, "", "google.com", "http.google.com", "www.google.com", "https://google.com" };
        for (String string : strings) {
            if (httpLink(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "http://google.com", "http://google.com/api/auth?name=Name&lastName=LastName" };
        for (String string : strings) {
            if (!httpLink(string)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
