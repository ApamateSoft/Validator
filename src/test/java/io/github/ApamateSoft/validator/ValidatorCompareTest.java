package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorCompareTest {

    private final static Validator validator = new Validator.Builder()
            .required()
            .build();

    @Test
    void exceptionIsExpectedIfTextDoesNotMatch() {
        assertThrows(InvalidEvaluationException.class, () -> validator.compareOrFail("abc", "xyz") );
    }

    @Test
    void ExceptionIsExpectedIfTextIsEmpty() {
        assertThrows(InvalidEvaluationException.class, () -> validator.compareOrFail("", "") );
    }

    @Test
    void noExceptionExpectedIfTextMatches() {
        assertDoesNotThrow(() -> validator.compareOrFail("abc", "abc") );
    }

    @Test
    void returnsFalseForStringThatDoesNotMatch() {
        assertFalse(validator.isMatch("abc", "xyz"));
    }

    @Test
    void returnsTrueForMatchingText() {
        assertTrue(validator.isMatch("abc", "abc"));
    }

    @Test
    void verifyCallback() {
        final OnInvalidEvaluation onInvalidEvaluation = mock(OnInvalidEvaluation.class);
        validator.onInvalidEvaluation(onInvalidEvaluation);
        validator.isMatch("abc", "xyz");
        verify(onInvalidEvaluation).invoke("Not match");
    }

}
