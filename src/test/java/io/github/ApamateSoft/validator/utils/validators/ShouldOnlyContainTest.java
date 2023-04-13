package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Alphabets;
import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ShouldOnlyContainTest {

    private static final String condition = Alphabets.OCT;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "012345678", "/*" };
        for (String string : strings) {
            if (Validators.shouldOnlyContain(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "01234567", "00" };
        for (String string : strings) {
            if (!Validators.shouldOnlyContain(string, condition)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
