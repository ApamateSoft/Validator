package com.apamatesoft.validator;

import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.functions.OnInvalidEvaluation;
import com.apamatesoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorTime12Test {

    final String[] NOT_PERMIT = { null, "", "1200", "01/01/2020", "12-30", "12.50", "25:00", "13:00 am", "23:59", "00:00", "23:59" };
    final String[] PERMIT = { "12:59 am", "1:00 pm", "01:00AM", "01:00pm" };

    private static final String MESSAGES = new MessagesEn().getTime12Message();

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.time12();

        builder = new Validator.Builder()
            .time12()
            .build();

    }

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(validator::isValid));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(validator::isValid));
    }

    @Test
    void notPermit_Builder() {
        assertFalse(stream(NOT_PERMIT).anyMatch(builder::isValid));
    }

    @Test
    void permit_Builder() {
        assertTrue(stream(PERMIT).allMatch(builder::isValid));
    }

    @Test
    void verifyCallback() {
        OnInvalidEvaluation onInvalidEvaluation = mock(OnInvalidEvaluation.class);
        validator.onInvalidEvaluation(onInvalidEvaluation);
        validator.isValid(null);
        verify(onInvalidEvaluation).invoke( MESSAGES );
    }

    @Test
    void verifyCallback_Builder() {
        OnInvalidEvaluation onInvalidEvaluation = mock(OnInvalidEvaluation.class);
        builder.onInvalidEvaluation(onInvalidEvaluation);
        builder.isValid(null);
        verify(onInvalidEvaluation).invoke( MESSAGES );
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
