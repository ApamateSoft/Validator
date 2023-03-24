package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static io.github.ApamateSoft.validator.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MustContainMinContainerAnnotationTest {

    private static final int MIN = 3;

    @MustContainMin(min = MIN, condition = ALPHA_LOWERCASE)
    @MustContainMin(min = MIN, condition = ALPHA_UPPERCASE)
    @MustContainMin(min = MIN, condition = NUMBER)
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