package com.apamatesoft.validator.utils.validators;

import org.junit.jupiter.api.Test;
import static com.apamatesoft.validator.utils.Validators.required;
import static org.junit.jupiter.api.Assertions.*;

public class RequiredTest {

    @Test
    void notPermit() {
        final String[] strings = { null, "",  };
        for (String string : strings) {
            if (required(string)) {
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
            if (!required(string)) {
                fail();
                break;
            }
        }
        assertTrue(required("xxx"));
    }

}
