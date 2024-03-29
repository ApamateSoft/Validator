package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RangeValueTest {

    private static final int MIN = 10;
    private static final int MAX = 30;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "9", "31" };
        for (String string : strings) {
            if (Validators.rangeValue(string, MIN, MAX)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { "10", "30", "20" };
        for (String string : strings) {
            if (!Validators.rangeValue(string, MIN, MAX)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}
