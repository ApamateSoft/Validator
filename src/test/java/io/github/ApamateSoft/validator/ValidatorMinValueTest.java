package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorMinValueTest {

    private static final double CONDITION = 2.5;
    private static final String[] NOT_PERMIT = { null, "", "text", "2,5", "2.49", "0", "-2.5" };
    private static final String[] PERMIT = { "2.5", "2.51", "30" };
    private static final String MESSAGES = String.format(new MessagesEn().getMinValueMessage(), CONDITION);

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.minValue(CONDITION);

        builder = new Validator.Builder()
                .minValue(CONDITION)
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