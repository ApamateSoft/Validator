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

public class ValidatorNumberPatternTest {

    private static final String[] NOT_PERMIT = { null, "", "example", "128", "+58 (412) 756-41-79 ", " +58 (412) 756-41-79", "+a8 (412) 756-41-79" };
    private static final String[] PERMIT = {"+12 (345) 678-90-12", "+11 (111) 111-11-11", "+xx (345) 678-90-12", "+xx (xxx) xxx-xx-xx" };
    private static final String PATTERN = "+xx (xxx) xxx-xx-xx";
    private static final String MESSAGES = String.format(new MessagesEn().getNumberPatternMessage(), PATTERN);

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.numberPattern(PATTERN);

        builder = new Validator.Builder()
                .numberPattern(PATTERN)
                .build();

    }

    @Test
    void notPermit() {
        for (String s : NOT_PERMIT)
            if (validator.isValid(s)) {
                System.out.println(">>: s: "+s);
                fail();
                break;
            }
        assertTrue(true);
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
        assertTrue(true);
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
