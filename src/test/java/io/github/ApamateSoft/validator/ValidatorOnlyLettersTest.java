package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import io.github.ApamateSoft.validator.utils.Alphabets;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorOnlyLettersTest {

    private static final String[] NOT_PERMIT = {  null, "", "12", "*/", "a1", "a-", "-1.61", "$10,320.00" };
    private static final String[] PERMIT = { Alphabets.ALPHABET };
    private static final String MESSAGES = new MessagesEn().getOnlyLettersMessage();

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.onlyLetters();

        builder = new Validator.Builder()
            .onlyLetters()
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
        assertThrows(InvalidEvaluationException.class, () -> validator.validOrFail("key",null) );
    }

    @Test
    void throwInvalidEvaluationException_Builder() {
        assertThrows(InvalidEvaluationException.class, () -> builder.validOrFail("key",null) );
    }

}
