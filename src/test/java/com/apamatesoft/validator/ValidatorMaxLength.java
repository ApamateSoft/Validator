package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorMaxLength {

    private final static Validator validator = new Validator();

    @BeforeAll
    static void beforeAll() {
        validator.maxLength(2);
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(validator.isValid(null));
    }

    @Test
    void returnTrueForStringWithLengthLessThan2() {
        assertTrue(validator.isValid("x"));
    }

    @Test
    void returnFalseForStringWithLengthGreaterThan2() {
        assertFalse(validator.isValid("xxx"));
    }

    @Test
    void returnTrueForStringWithLengthEqualThan2() {
        assertTrue(validator.isValid("xx"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("It requires less than 2 characters");
    }

}
