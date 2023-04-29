package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.functions.OnInvalidEvaluation;
import io.github.ApamateSoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorHttpLinkTest {

    private static final String[] NOT_PERMIT = {  null, "", "google.com", "http.google.com", "www.google.com", "https://google.com" };
    private static final String[] PERMIT = { "http://google.com", "http://google.com/api/auth?name=Name&lastName=LastName" };
    private static final String MESSAGES = new MessagesEn().getHttpLinkMessage();

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.httpLink();

        builder = new Validator.Builder()
                .httpLink()
                .build();

    }

    @Test
    void notPermit() {
        boolean b = stream(NOT_PERMIT).anyMatch(validator::isValid);
        assertFalse(b);
    }

    @Test
    void permit() {
        boolean b = stream(PERMIT).allMatch(validator::isValid);
        assertTrue(b);
    }

    @Test
    void notPermit_Builder() {
        boolean b = stream(NOT_PERMIT).anyMatch(builder::isValid);
        assertFalse(b);
    }

    @Test
    void permit_Builder() {
        boolean b = stream(PERMIT).allMatch(builder::isValid);
        assertTrue(b);
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
