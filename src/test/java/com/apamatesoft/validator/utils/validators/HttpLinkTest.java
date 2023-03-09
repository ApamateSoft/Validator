package com.apamatesoft.validator.utils.validators;

import com.apamatesoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class HttpLinkTest {

    private static final String[] NOT_PERMIT = { null, "", "google.com", "http.google.com", "www.google.com", "https://google.com" };
    private static final String[] PERMIT = { "http://google.com", "http://google.com/api/auth?name=Name&lastName=LastName" };

    @Test
    void notPermit() {
        boolean b = stream(NOT_PERMIT).anyMatch(Validators::httpLink);
        assertFalse(b);
    }

    @Test
    void permit() {
        boolean b = stream(PERMIT).allMatch(Validators::httpLink);
        assertTrue(b);
    }

}
