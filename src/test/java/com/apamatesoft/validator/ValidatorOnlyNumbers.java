package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorOnlyNumbers {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuilder = new Validator.Builder()
            .onlyNumbers()
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.onlyNumbers();
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
    void returnFalseForStringWithCharacters() {
        assertFalse(validator.isValid("xxx"));
    }

    @Test
    void returnFalseForStringWithCharactersAndNumbers() {
        assertFalse(validator.isValid("xxx123"));
    }

    @Test
    void returnTrueForStringWithOnlyNumbers() {
        assertTrue(validator.isValid("123"));
    }

    @Test
    void returnFalseForStringWithDecimalFormat() {
        assertFalse(validator.isValid("0.5"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("Just numbers");
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
    void returnFalseForStringWithCharacters_build() {
        assertFalse(validatorBuilder.isValid("xxx"));
    }

    @Test
    void returnFalseForStringWithCharactersAndNumbers_build() {
        assertFalse(validatorBuilder.isValid("xxx123"));
    }

    @Test
    void returnTrueForStringWithOnlyNumbers_build() {
        assertTrue(validatorBuilder.isValid("123"));
    }

    @Test
    void returnFalseForStringWithDecimalFormat_build() {
        assertFalse(validatorBuilder.isValid("0.5"));
    }

    @Test
    void verifyCallback_build() {
        final NotPass notPass = mock(NotPass.class);
        validatorBuilder.onNotPass(notPass);
        validatorBuilder.isValid(null);
        verify(notPass).invoke("Just numbers");
    }

}