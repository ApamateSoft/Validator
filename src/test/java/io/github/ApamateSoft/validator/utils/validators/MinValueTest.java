package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

public class MinValueTest {

    private static final String[] NOT_PERMIT = { null, "", "text", "2,5", "2.49", "0", "-2.5" };
    private static final String[] PERMIT = { "2.5", "2.51", "30" };
    private static final double CONDITION = 2.5;

    @Test
    void notPermit() {
        assertFalse( stream(NOT_PERMIT).anyMatch( it -> Validators.minValue(it, CONDITION) ) );
    }

    @Test
    void permit() {
        assertTrue( stream(PERMIT).allMatch( it -> Validators.minValue(it, CONDITION) ) );
    }

}
