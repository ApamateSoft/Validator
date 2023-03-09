package com.apamatesoft.validator.utils.validators;

import com.apamatesoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class HttpsLinkTest {

    private static final String[] NOT_PERMIT = { null, "", "google.com", "http.google.com", "https.google.com", "www.google.com", "http://google.com" };
    private static final String[] PERMIT = { "https://google.com", "https://google.com/api/auth?name=Name&lastName=LastName" };

    @Test
    void notPermit() {
        assertFalse( stream(NOT_PERMIT).anyMatch(Validators::httpsLink) );
    }

    @Test
    void permit() {
        assertTrue( stream(PERMIT).allMatch(Validators::httpsLink) );
    }

}