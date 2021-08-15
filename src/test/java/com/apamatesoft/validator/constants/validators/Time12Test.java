package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.time12;
import static org.junit.jupiter.api.Assertions.*;

public class Time12Test {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "1200", "01/01/2020", "12-30", "12.50", "25:00", "13:00 am", "23:59", "00:00", "23:59" };
        for (String string : strings) {
            if (time12(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "12:59 am", "1:00 pm", "01:00AM", "01:00pm" };
        for (String string : strings) {
            if (!time12(string)) {
                System.out.println(string);
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
