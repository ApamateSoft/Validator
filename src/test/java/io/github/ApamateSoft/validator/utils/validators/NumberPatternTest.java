package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.fail;

public class NumberPatternTest {

    @Test
    void notPermit() {
        final String[] strings = {
                null,
                "",
                "example",
                "128",
                "+58 (412) 756-41-79 ",
                " +58 (412) 756-41-79",
                "+a8 (412) 756-41-79"
        };
        for (String string : strings) {
            if (Validators.numberPattern(string, "+xx (xxx) xxx-xx-xx")) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = {
            "21/09/1991",
            "15/25/1991"
        };
        for (String string : strings) {
            if (!Validators.numberPattern(string, "xx/xx/xxxx")) {
                fail();
                break;
            }
        }
        Assertions.assertTrue(true);
    }

}
