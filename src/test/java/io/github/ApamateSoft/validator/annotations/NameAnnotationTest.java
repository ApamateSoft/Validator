package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NameAnnotationTest {

    private static final String MESSAGES = new MessagesEn().getNameMessage();

    @Name()
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
        s = "David";
        assertDoesNotThrow(() -> validOrFail(this));
    }

}
