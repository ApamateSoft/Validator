package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LengthTest {

    final int condition = 3;

    @Test
    void notPermit() {
        final String[] strings = { null, "", "12", "1234" };
        for (String string : strings) {
            if (Validators.length(string, condition)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        Assertions.assertTrue(Validators.length("123", condition));
    }

}