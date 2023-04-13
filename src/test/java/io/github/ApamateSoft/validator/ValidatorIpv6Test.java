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

public class ValidatorIpv6Test {

    private static final String[] NOT_PERMIT = {
        null,
        "",
        "text",
        "128",
        "10.0.0.256",
        "10.0.0.0.1",
        "127.0.0.1",
        "192.168.0.109",
        "10.0.0.1",
        "ffff::ffff::ffff",
        "fffff::ffff",
        "fffg::ffff"
    };
    private static final String[] PERMIT = {
        "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff",
        "ffff::",
        "ffff::ffff",
        "ffff:ffff::ffff"
    };
    private static final String MESSAGES = new MessagesEn().getIpv6Message();

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.ipv6();

        builder = new Validator.Builder()
            .ipv6()
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
        assertThrows(InvalidEvaluationException.class, () -> validator.validOrFail(null) );
    }

    @Test
    void throwInvalidEvaluationException_Builder() {
        assertThrows(InvalidEvaluationException.class, () -> builder.validOrFail(null) );
    }

}
