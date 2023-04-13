package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static io.github.ApamateSoft.validator.utils.Alphabets.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NotContainContainerAnnotationTest {


    @NotContain(alphabet = ALPHA_UPPERCASE)
    @NotContain(alphabet = ALPHA_LOWERCASE)
    @NotContain(alphabet = NUMBER)
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
        s = "@";
        assertDoesNotThrow(() -> validOrFail(this));
    }

}
