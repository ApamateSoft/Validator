package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorMinLengthTest {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuild = new Validator.Builder()
            .minLength(2)
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.minLength(2);
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(validator.isValid(null));
    }

    @Test
    void returnFalseForStringWithLengthLessThan2() {
        assertFalse(validator.isValid("x"));
    }

    @Test
    void returnTrueForStringWithLengthGreaterThan2() {
        assertTrue(validator.isValid("xxx"));
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
        verify(notPass).invoke("It requires at least 2 characters");
    }

    @Test
    void returnFalseForNullValue_build() {
        assertFalse(validatorBuild.isValid(null));
    }

    @Test
    void returnFalseForStringWithLengthLessThan2_build() {
        assertFalse(validatorBuild.isValid("x"));
    }

    @Test
    void returnTrueForStringWithLengthGreaterThan2_build() {
        assertTrue(validatorBuild.isValid("xxx"));
    }

    @Test
    void returnTrueForStringWithLengthEqualThan2_build() {
        assertTrue(validatorBuild.isValid("xx"));
    }

    @Test
    void verifyCallback_build() {
        final NotPass notPass = mock(NotPass.class);
        validatorBuild.onNotPass(notPass);
        validatorBuild.isValid(null);
        verify(notPass).invoke("It requires at least 2 characters");
    }

}