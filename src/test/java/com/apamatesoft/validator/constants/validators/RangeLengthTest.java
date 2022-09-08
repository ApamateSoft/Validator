package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static com.apamatesoft.validator.constants.Validators.rangeLength;
import static org.junit.jupiter.api.Assertions.*;

public class RangeLengthTest {

    private static final int MIN = 3;
    private static final int MAX = 5;
    private static final String[] NOT_PERMIT = { null, "", "12", "123456" };
    private static final String[] PERMIT = { "123", "1234" ,"12345" };

    @Test
    void notPermit() {
        boolean b = stream(NOT_PERMIT).anyMatch( it -> rangeLength(it, MIN, MAX) );
        assertFalse(b);
    }

    @Test
    void permit() {
        boolean b = stream(PERMIT).allMatch( it -> rangeLength(it, MIN, MAX) );
        assertTrue(b);
    }

}
