package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Alphabets;
import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NotContainTest {

    private static final String condition = Alphabets.OCT;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "0", "1", "2", "3", "4", "5", "6", "7", "text4" };
        for (String string : strings) {
            if (Validators.notContain(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "89", "text", "@nic89" };
        for (String string : strings) {
            if (!Validators.notContain(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
