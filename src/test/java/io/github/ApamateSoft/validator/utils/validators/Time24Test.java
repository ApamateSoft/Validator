package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class Time24Test {

    private static final String[] NOT_PERMIT = { null, "", "text", "12:59 am", "1:00 pm", "01:00AM", "01:00pm", "1200", "01/01/2020", "12-30", "12.50", "25:00", "13:00 am" };
    private static final String[] PERMIT = { "13:00", "23:59", "00:00" };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::time24));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(Validators::time24));
    }

}
