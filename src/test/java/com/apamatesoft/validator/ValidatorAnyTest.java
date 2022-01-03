package com.apamatesoft.validator;

import com.apamatesoft.validator.constants.Validators;
import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.length;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorAnyTest {

    private final static Validator validator = new Validator.Builder()
            .any(
                "error message",
                Validators::email,
                it -> length(it, 5)
            )
            .build();

    @Test
    void permit() {
        String evaluate = "aaaaa";
        assertTrue(validator.isValid(evaluate));
    }

    @Test
    void notPermit() {
        String evaluate = "xxx";
        assertFalse(validator.isValid(evaluate));
    }


}
