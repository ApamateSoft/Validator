package com.apamatesoft.validator.constants.validators;

import com.apamatesoft.validator.constants.Validators;
import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Constants.ALPHA_NUMERIC;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class OnlyAlphanumericTest {

    private static final String[] NOT_PERMIT = { null, "", "-", "a*", ">text", "a-", "-1.61", "$10,320.00", "a b" };
    private static final String[] PERMIT = { ALPHA_NUMERIC };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::onlyAlphanumeric));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(Validators::onlyAlphanumeric));
    }

}
