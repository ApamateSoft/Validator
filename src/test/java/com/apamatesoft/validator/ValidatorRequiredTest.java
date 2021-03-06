package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class ValidatorRequiredTest {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuilder = new Validator.Builder()
            .required()
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.required();
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(validator.isValid(null));
    }

    @Test
    void returnFalseForEmptyValue() {
        assertFalse(validator.isValid(""));
    }

    @Test
    void returnTrueForAnyString() {
        assertTrue(validator.isValid("xxx"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("Required");
    }

    @Test
    void returnFalseForNullValue_build() {
        assertFalse(validatorBuilder.isValid(null));
    }

    @Test
    void returnFalseForEmptyValue_build() {
        assertFalse(validatorBuilder.isValid(""));
    }

    @Test
    void returnTrueForAnyString_build() {
        assertTrue(validatorBuilder.isValid("xxx"));
    }

    @Test
    void verifyCallback_build() {
        final NotPass notPass = mock(NotPass.class);
        validatorBuilder.onNotPass(notPass);
        validatorBuilder.isValid(null);
        verify(notPass).invoke("Required");
    }

}