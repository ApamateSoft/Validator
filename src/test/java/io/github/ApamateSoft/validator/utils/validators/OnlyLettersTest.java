package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import io.github.ApamateSoft.validator.utils.Constants;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class OnlyLettersTest {

    private static final String[] NOT_PERMIT = {  null, "", "12", "*/", "a1", "a-", "-1.61", "$10,320.00" };
    private static final String[] PERMIT = { Constants.ALPHABET };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::onlyLetters));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(Validators::onlyLetters));
    }

}