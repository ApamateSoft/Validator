package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MinLengthTest {

    final int condition = 3;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "1", "12" };
        for (String string : strings) {
            if (Validators.minLength(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "123", "1234" };
        for (String string : strings) {
            if (!Validators.minLength(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
