package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class MaxValueTest {

    private static final String[] NOT_PERMIT = { null, "", "text", "2.51", "30", "91", "1,2", "2.6",  };
    private static final String[] PERMIT = { "2.5", "0.0", "-30", "2.49" };
    private static final double CONDITION = 2.5;

    @Test
    void notPermit() {
        assertFalse( stream(NOT_PERMIT).anyMatch( it -> Validators.maxValue(it, CONDITION) ) );
    }

    @Test
    void permit() {
        assertTrue( stream(PERMIT).allMatch( it -> Validators.maxValue(it, CONDITION) ) );
    }

}
