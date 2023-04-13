package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DateAnnotationTest {

    private static final String FORMAT = "dd/MM/yyyy";
    private static final String MESSAGES = format(new MessagesEn().getDateMessage(), FORMAT);

    @Date(format = FORMAT)
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
        s = "21/08/1991";
        assertDoesNotThrow(() -> validOrFail(this));
    }

}
