package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ValidatorEmailTest {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuild = new Validator.Builder()
            .email()
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.email();
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(validator.isValid(null));
    }

    @Test
    void returnFalseForStringWithDifferentEmailFormat() {
        assertFalse(validator.isValid("xxx"));
    }

    @Test
    void returnTrueForAnEmailFormattedString() {
        assertTrue(validator.isValid("example@mail.com"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("Email invalid");
    }

    @BeforeAll
    static void beforeAll_build() {
        validatorBuild.email();
    }

    @Test
    void returnFalseForNullValue_build() {
        assertFalse(validatorBuild.isValid(null));
    }

    @Test
    void returnFalseForStringWithDifferentEmailFormat_build() {
        assertFalse(validatorBuild.isValid("xxx"));
    }

    @Test
    void returnTrueForAnEmailFormattedString_build() {
        assertTrue(validatorBuild.isValid("example@mail.com"));
    }

    @Test
    void verifyCallback_build() {
        final NotPass notPass = mock(NotPass.class);
        validatorBuild.onNotPass(notPass);
        validatorBuild.isValid(null);
        verify(notPass).invoke("Email invalid");
    }

}
