package com.apamatesoft.validator.utils.validators;

import com.apamatesoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.utils.Constants.ALPHABET;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class OnlyCharactersTest {

    private static final String[] NOT_PERMIT = {  null, "", "12", "*/", "a1", "a-", "-1.61", "$10,320.00" };
    private static final String[] PERMIT = { ALPHABET };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::onlyCharacters));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch(Validators::onlyCharacters));
    }

}