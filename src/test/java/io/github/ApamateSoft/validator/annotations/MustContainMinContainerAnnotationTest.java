package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static io.github.ApamateSoft.validator.utils.Alphabets.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MustContainMinContainerAnnotationTest {

    private static final int MIN = 3;

    @MustContainMin(min = MIN, alphabet = ALPHA_LOWERCASE)
    @MustContainMin(min = MIN, alphabet = ALPHA_UPPERCASE)
    @MustContainMin(min = MIN, alphabet = NUMBER)
    private String s;

    @Test
    void throwInvalidEvaluationException() {
        s = null;
        assertThrows(
            InvalidEvaluationException.class,
            () -> validOrFail(this)
        );
    }

    @Test
    void pass() {
        s = "abcABC123";
        assertDoesNotThrow(() -> validOrFail(this));
    }

}