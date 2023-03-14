package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorRangeLengthTest {

    private static final int MIN = 3;
    private static final int MAX = 5;
    private static final String[] NOT_PERMIT = { null, "", "12", "123456" };
    private static final String[] PERMIT = { "123", "1234" ,"12345" };
    private static final String MESSAGES = String.format(new MessagesEn().getRangeLengthMessage(), MIN, MAX);

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.rangeLength(MIN, MAX);

        builder = new Validator.Builder()
            .rangeLength(MIN, MAX)
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
        assertTrue(true);
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
        assertTrue(true);
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
