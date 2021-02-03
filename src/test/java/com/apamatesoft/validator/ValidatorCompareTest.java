package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorCompareTest {

    private final static Validator validator = new Validator.Builder()
            .required()
            .build();

    @Test
    void returnsFalseForStringThatDoesNotMatch() {
        assertFalse(validator.compare("abc", "xyz"));
    }

    @Test
    void returnsTrueForMatchingText() {
        assertTrue(validator.compare("abc", "abc"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.compare("abc", "xyz");
        verify(notPass).invoke("Not match");
    }

}
