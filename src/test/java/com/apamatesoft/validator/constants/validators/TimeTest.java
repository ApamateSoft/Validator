package com.apamatesoft.validator.constants.validators;

import com.apamatesoft.validator.constants.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {

    private static final String[] NOT_PERMIT = { null, "", "1200", "01/01/2020", "12-30", "12.50", "25:00", "13:00 am" };
    private static final String[] PERMIT = { "00:00", "12:30", "12:59 am", "23:59", "1:00 pm", "01:00AM", "01:00pm", "01:00PM" };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::time));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(Validators::time));
    }

}
