package com.apamatesoft.validator;

import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.functions.NotPass;
import com.apamatesoft.validator.messages.MessagesEn;
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
        NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke(MESSAGES);
    }

    @Test
    void verifyCallback_Builder() {
        NotPass notPass = mock(NotPass.class);
        builder.onNotPass(notPass);
        builder.isValid(null);
        verify(notPass).invoke(MESSAGES);
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
