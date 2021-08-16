package com.apamatesoft.validator;

import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.functions.NotPass;
import com.apamatesoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ValidatorEmailTest {

    private static final String[] NOT_PERMIT = { null, "", "example", "@mail", "example@mail", "example@mail.", "mail.com", "@mail.com" };
    private static final String[] PERMIT = { "example@mail.com" };
    private static final String MESSAGES = new MessagesEn().getEmailMessage();

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.email();

        builder = new Validator.Builder()
                .email()
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
        NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke( MESSAGES );
    }

    @Test
    void verifyCallback_Builder() {
        NotPass notPass = mock(NotPass.class);
        builder.onNotPass(notPass);
        builder.isValid(null);
        verify(notPass).invoke( MESSAGES );
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
