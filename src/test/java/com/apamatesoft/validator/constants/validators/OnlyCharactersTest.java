package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Constants.ALPHABET;
import static com.apamatesoft.validator.constants.Validators.onlyCharacters;
import static org.junit.jupiter.api.Assertions.*;

public class OnlyCharactersTest {

    @Test
    void notPermit() {
        final String[] strings = {  null, "", "12", "*/", "a1", "a-", "-1.61", "$10,320.00" };
        for (String string : strings) {
            if (onlyCharacters(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        assertTrue(onlyCharacters(ALPHABET));
    }

}
