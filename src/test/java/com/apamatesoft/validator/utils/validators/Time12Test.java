package com.apamatesoft.validator.utils.validators;

import com.apamatesoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class Time12Test {

    final String[] NOT_PERMIT = { null, "", "1200", "01/01/2020", "12-30", "12.50", "25:00", "13:00 am", "23:59", "00:00", "23:59" };
    final String[] PERMIT = { "12:59 am", "1:00 pm", "01:00AM", "01:00pm" };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::time12));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(Validators::time12));
    }

}
