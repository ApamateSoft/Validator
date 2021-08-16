package com.apamatesoft.validator;

import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.functions.NotPass;
import com.apamatesoft.validator.messages.MessagesEn;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorMinLengthTest {

    private static final int CONDITION = 3;
    private static final String[] NOT_PERMIT = { null, "", "1", "12" };
    private static final String[] PERMIT = { "123", "1234" };
    private static final String MESSAGES = format(new MessagesEn().getMinLengthMessage(), CONDITION);

    private Validator validator, builder;

    @BeforeEach
    void before() {
        validator = new Validator();
        validator.minLength(CONDITION);

        builder = new Validator.Builder()
                .minLength(CONDITION)
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