package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorLengthTest {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuild = new Validator.Builder()
            .length(2)
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.length(2);
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
        verify(notPass).invoke("It requires 2 characters");
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
    void returnFalseForStringWithLengthGreaterThan2_build() {
        assertFalse(validatorBuild.isValid("xxx"));
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
        verify(notPass).invoke("It requires 2 characters");
    }

}