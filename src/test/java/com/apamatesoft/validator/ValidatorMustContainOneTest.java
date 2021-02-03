package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorMustContainOneTest {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuilder = new Validator.Builder()
            .mustContainOne("abc")
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.mustContainOne("abc");
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
    void returnTrueForStringContainingA() {
        assertTrue(validator.isValid("adf"));
    }

    @Test
    void returnTrueForStringContainingABC_andMore() {
        assertTrue(validator.isValid("abcxyz"));
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
        verify(notPass).invoke("At least one of the following characters is required: abc");
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
    void returnTrueForStringContainingA_build() {
        assertTrue(validatorBuilder.isValid("adf"));
    }

    @Test
    void returnTrueForStringContainingABC_andMore_build() {
        assertTrue(validatorBuilder.isValid("abcxyz"));
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
        verify(notPass).invoke("At least one of the following characters is required: abc");
    }

}
