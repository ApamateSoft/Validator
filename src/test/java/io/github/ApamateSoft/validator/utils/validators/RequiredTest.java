package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequiredTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "",  };
        for (String string : strings) {
            if (Validators.required(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = { " ", "xxx", "123", "Name Lastname", "@nick", "@nick01", "@nick_01" };
        for (String string : strings) {
            if (!Validators.required(string)) {
                fail();
                break;
            }
        }
        Assertions.assertTrue(Validators.required("xxx"));
    }

}
