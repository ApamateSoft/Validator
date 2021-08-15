package com.apamatesoft.validator;

import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.max;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorMaxTest {

    private final static NotPass notPass = mock(NotPass.class);
    private final static Validator validator = new Validator();
    private final static Validator validatorBuild = new Validator.Builder()
            .max(5)
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.max(5);
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(max(null, 5));
    }

    @Test
    void returnsFalseForStringDifferentFromNumber() {
        assertFalse(max("abc", 5));
    }

    @Test
    void returnsFalseForValuesGreaterThanCondition() {
        assertFalse(max("6", 5));
    }

    @Test
    void returnsTrueForValuesLessThanTheCondition() {
        assertTrue(max("2", 5));
    }

    @Test
    void ExceptionIsExpectedIfTextIsEmpty() {
        assertThrows(InvalidEvaluationException.class, () -> validator.isValidOrFail(null) );
    }

    @Test
    void noExceptionExpectedIfTextMatches() {
        assertDoesNotThrow( () -> validator.isValidOrFail("1") );
    }

    @Test
    void verifyCallback() {
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("The value cannot be greater than 5.00");
    }

    @Test
    void verifyCallback_build() {
        validatorBuild.onNotPass(notPass);
        validatorBuild.isValid(null);
        verify(notPass).invoke("The value cannot be greater than 5.00");
    }

}
