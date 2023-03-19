package io.github.ApamateSoft.validator;

import io.github.ApamateSoft.validator.Validator;
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

public class ValidatorsHttpsLinkTest {

    private static final String[] NOT_PERMIT = { null, "", "google.com", "http.google.com", "https.google.com", "www.google.com", "http://google.com" };
    private static final String[] PERMIT = { "https://google.com", "https://google.com/api/auth?name=Name&lastName=LastName" };
    private static final String MESSAGES = new MessagesEn().getHttpsLinkMessage();

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.httpsLink();

        builder = new Validator.Builder()
            .httpsLink()
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
