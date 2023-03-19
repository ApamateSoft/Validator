package io.github.ApamateSoft.validator.utils.validators;

import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.utils.Validators.expirationDate;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ExpirationDateTest {

    private static final String[] NOT_PERMIT = {
        null,
        "",
        "example",
        "21091991",
        "21-09-1991",
        "1991/09/21",
        "09/21/1991"
    };
    private static final String[] PERMIT = { "08/99" };
    private static final String FORMAT ="MM/yy";

    @Test
    void notPermit() {
        boolean b = stream(NOT_PERMIT).anyMatch( it -> expirationDate(it, FORMAT) );
        assertFalse(b);
    }

    @Test
    void permit() {
        boolean b = stream(PERMIT).allMatch( it -> expirationDate(it, FORMAT) );
        assertTrue(b);
    }

}
