package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorShouldOnlyContain {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuilder = new Validator.Builder()
            .shouldOnlyContain("abc")
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.shouldOnlyContain("abc");
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
    void returnFalseForStringWithoutAbcCharacters() {
        assertFalse(validator.isValid("dfe"));
    }

    @Test
    void returnFalseForStringContainingA() {
        assertFalse(validator.isValid("adf"));
    }

    @Test
    void returnFalseForStringContainingABC_andMore() {
        assertFalse(validator.isValid("abcxyz"));
    }

    @Test
    void returnTrueForStringOnlyWith_abc() {
        assertTrue(validator.isValid("abccbbabc"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("They are just admitted the following characters abc");
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
    void returnFalseForStringWithoutAbcCharacters_build() {
        assertFalse(validatorBuilder.isValid("dfe"));
    }

    @Test
    void returnFalseForStringContainingA_build() {
        assertFalse(validatorBuilder.isValid("adf"));
    }

    @Test
    void returnFalseForStringContainingABC_andMore_build() {
        assertFalse(validatorBuilder.isValid("abcxyz"));
    }

    @Test
    void returnTrueForStringOnlyWith_abc_build() {
        assertTrue(validatorBuilder.isValid("abccbbabc"));
    }

    @Test
    void verifyCallback_build() {
        final NotPass notPass = mock(NotPass.class);
        validatorBuilder.onNotPass(notPass);
        validatorBuilder.isValid(null);
        verify(notPass).invoke("They are just admitted the following characters abc");
    }


}