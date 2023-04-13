package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static io.github.ApamateSoft.validator.utils.Alphabets.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MustContainOneContainerTest {

    @MustContainOne(alphabet = ALPHA_LOWERCASE)
    @MustContainOne(alphabet = ALPHA_UPPERCASE)
    @MustContainOne(alphabet = NUMBER)
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
        s = "aA0";
        assertDoesNotThrow(() -> validOrFail(this));
    }

}
