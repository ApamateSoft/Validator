package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OnlyNumbersTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "", "text", "Name Lastname", "1a", "a1", "1a1", "a1a", "1.00", "1,00" };
        for (String string : strings) {
            if (Validators.onlyNumbers(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        Assertions.assertTrue(Validators.onlyNumbers("123456789"));
    }

}