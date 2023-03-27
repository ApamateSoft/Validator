package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.Validator.validOrFail;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CompareAnnotationTest {

    private static final String MESSAGES = new MessagesEn().getCompareMessage();

    @Compare(compareWith = "passwordConfirm")
    private String password;
    private String passwordConfirm;

    @Test
    void throwInvalidEvaluationException() {
        password = "abc";
        passwordConfirm = "xyz";
        assertThrows(
            InvalidEvaluationException.class,
            () -> validOrFail(this)
        );
    }

    @Test
    void verifyMessage() {
        password = "abc";
        passwordConfirm = "xyz";
        try {
            validOrFail(this);
            fail();
        } catch (InvalidEvaluationException e) {
            assertEquals(e.getMessage(), MESSAGES);
        }
    }

    @Test
    void pass() {
        password = "abc";
        passwordConfirm = "abc";
        assertDoesNotThrow(() -> validOrFail(this));
    }

}
