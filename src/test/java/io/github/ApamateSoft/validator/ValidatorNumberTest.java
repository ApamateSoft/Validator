package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorNumberTest {

    private static final String[] NOT_PERMIT = { null, "", "text", "a1", "1a", "12345,6789", "123.456.789" };
    private static final String[] PERMIT = { "123456789", "-123456789", "12345.6789", "-12345.6789", "1" };
    private static final String MESSAGES = new MessagesEn().getNumberMessage();

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.number();

        builder = new Validator.Builder()
                .number()
                .build();

    }

    @Test
    void notPermit() {
        for (String s : NOT_PERMIT)
            if (validator.isValid(s)) {
                fail();
                break;
            }
        assertFalse(false);
    }

    @Test
    void permit() {
        for (String string : PERMIT)
            if (!validator.isValid(string)) {
                fail();
                break;
            }
        Assertions.assertTrue(true);
    }

    @Test
    void notPermit_Builder() {
        for (String s : NOT_PERMIT)
            if (builder.isValid(s)) {
                fail();
                break;
            }
        assertFalse(false);
    }

    @Test
    void permit_Builder() {
        for (String string : PERMIT)
            if (!builder.isValid(string)) {
                fail();
                break;
            }
        Assertions.assertTrue(true);
    }

    @Test
    void verifyCallback() {
        OnInvalidEvaluation onInvalidEvaluation = mock(OnInvalidEvaluation.class);
        validator.onInvalidEvaluation(onInvalidEvaluation);
        validator.isValid(null);
        verify(onInvalidEvaluation).invoke(MESSAGES);
    }

    @Test
    void verifyCallback_Builder() {
        OnInvalidEvaluation onInvalidEvaluation = mock(OnInvalidEvaluation.class);
        builder.onInvalidEvaluation(onInvalidEvaluation);
        builder.isValid(null);
        verify(onInvalidEvaluation).invoke(MESSAGES);
    }

    @Test
    void throwInvalidEvaluationException() {
        assertThrows(InvalidEvaluationException.class, () -> validator.isValidOrFail(null) );
    }

    @Test
    void throwInvalidEvaluationException_Builder() {
        assertThrows(InvalidEvaluationException.class, () -> builder.isValidOrFail(null) );
    }


}
