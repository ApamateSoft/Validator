package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class DateTest {

    private static final String[] NOT_PERMIT = { null, "", "example", "21091991", "21-09-1991", "1991/09/21", "09/21/1991" };
    private static final String[] PERMIT = { "21/08/1991" };
    private static final String FORMAT ="dd/MM/yyyy";

    @Test
    void notPermit() {
        boolean b = stream(NOT_PERMIT).anyMatch( it -> Validators.date(it, FORMAT) );
        assertFalse(b);
    }

    @Test
    void permit() {
        boolean b = stream(PERMIT).allMatch( it -> Validators.date(it, FORMAT) );
        assertTrue(b);
    }

}
