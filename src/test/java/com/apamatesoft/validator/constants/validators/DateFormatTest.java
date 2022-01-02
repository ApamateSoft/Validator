package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.apamatesoft.validator.constants.Validators.dateFormat;
import static org.junit.jupiter.api.Assertions.*;

public class DateFormatTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "example", "", "21091991", "21-09-1991", "1991/09/21", "09/21/1991" };
        final boolean b = Arrays.stream(strings)
                .anyMatch( it -> dateFormat(it, "dd/MM/yyyy") );
        assertFalse(b);
    }

    @Test
    void permit() {
        final String[] strings = { "21/08/1991" };
        for (String string : strings) {
            if (!dateFormat(string, "dd/MM/yyyy")) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
