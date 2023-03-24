package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static io.github.ApamateSoft.validator.utils.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MustContainOneContainerTest {

    @MustContainOne(condition = ALPHA_LOWERCASE)
    @MustContainOne(condition = ALPHA_UPPERCASE)
    @MustContainOne(condition = NUMBER)
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
