package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Alphabets;
import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MustContainOneTest {

    private static final String condition = Alphabets.OCT;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "@nick", "@nick89" };
        for (String string : strings) {
            if (Validators.mustContainOne(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "0", "@nick1", "91" };
        for (String string : strings) {
            if (!Validators.mustContainOne(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
