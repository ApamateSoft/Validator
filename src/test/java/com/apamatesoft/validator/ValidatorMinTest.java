package com.apamatesoft.validator;

import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.ls;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorMinTest {

    private final static NotPass notPass = mock(NotPass.class);
    private final static Validator validator = new Validator();
    private final static Validator validatorBuild = new Validator.Builder()
            .min(5)
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.min(5);
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(ls(null, 5));
    }

    @Test
    void returnsFalseForStringDifferentFromNumber() {
        assertFalse(ls("abc", 5));
    }

    @Test
    void returnsTrueForValuesGreaterThanCondition() {
        assertTrue(ls("6", 5));
    }

    @Test
    void returnsFalseForValuesLessThanTheCondition() {
        assertFalse(ls("2", 5));
    }

    @Test
    void ExceptionIsExpectedIfTextIsEmpty() {
        assertThrows(InvalidEvaluationException.class, () -> validator.isValidOrFail(null) );
    }

    @Test
    void noExceptionExpectedIfTextMatches() {
        assertDoesNotThrow( () -> validator.isValidOrFail("6") );
    }

    @Test
    void verifyCallback() {
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("The value cannot be less than 5.00");
    }

    @Test
    void verifyCallback_build() {
        validatorBuild.onNotPass(notPass);
        validatorBuild.isValid(null);
        verify(notPass).invoke("The value cannot be less than 5.00");
    }

}