package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MaxLengthAnnotationTest {

    private static final int MAX = 3;
    private static final String MESSAGES = format(new MessagesEn().getMaxLengthMessage(), MAX);

    @MaxLength(max = MAX)
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
    void verifyMessage() {
        s = null;
        try {
            validOrFail(this);
            fail();
        } catch (InvalidEvaluationException e) {
            assertEquals(e.getMessage(), MESSAGES);
        }
    }

    @Test
    void pass() {
        s = "123";
        assertDoesNotThrow(() -> validOrFail(this));
    }

}