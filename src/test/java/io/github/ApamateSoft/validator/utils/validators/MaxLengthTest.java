package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MaxLengthTest {

    final int condition = 3;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "1234" };
        for (String string : strings) {
            if (Validators.maxLength(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "1", "12", "123" };
        for (String string : strings) {
            if (!Validators.maxLength(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
