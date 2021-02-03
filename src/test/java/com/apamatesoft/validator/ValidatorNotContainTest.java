package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorNotContainTest {

    private final static Validator validator = new Validator();
    private final static Validator validatorBuild = new Validator.Builder()
            .notContain("abc")
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.notContain("abc");
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
    void returnTrueForStringWithoutAbcCharacters() {
        assertTrue(validator.isValid("dfe"));
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
    void returnFalseForStringOnlyWith_abc() {
        assertFalse(validator.isValid("abccbbabc"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("The following characters aren't admitted abc");
    }

    @Test
    void returnFalseForNullValue_build() {
        assertFalse(validatorBuild.isValid(null));
    }

    @Test
    void returnFalseForEmptyValue_build() {
        assertFalse(validatorBuild.isValid(""));
    }

    @Test
    void returnTrueForStringWithoutAbcCharacters_build() {
        assertTrue(validatorBuild.isValid("dfe"));
    }

    @Test
    void returnFalseForStringContainingA_build() {
        assertFalse(validatorBuild.isValid("adf"));
    }

    @Test
    void returnFalseForStringContainingABC_andMore_build() {
        assertFalse(validatorBuild.isValid("abcxyz"));
    }

    @Test
    void returnFalseForStringOnlyWith_abc_build() {
        assertFalse(validatorBuild.isValid("abccbbabc"));
    }

    @Test
    void verifyCallback_build() {
        final NotPass notPass = mock(NotPass.class);
        validatorBuild.onNotPass(notPass);
        validatorBuild.isValid(null);
        verify(notPass).invoke("The following characters aren't admitted abc");
    }

}
