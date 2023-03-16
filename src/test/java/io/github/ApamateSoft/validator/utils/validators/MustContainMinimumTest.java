package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.utils.Constants.ALPHA_LOWERCASE;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MustContainMinimumTest {

    private static final String[] NOT_PERMIT = { null, "", "ABC", "123", "abC" };
    private static final String[] PERMIT = { "abc", "abcd", "aBcDe", "abcABC123..." };
    private static final int MIN = 3;

    @Test
    void test() {
        boolean b = stream(NOT_PERMIT).anyMatch( it -> Validators.mustContainMinimum(it, MIN, ALPHA_LOWERCASE) );
        assertFalse(b);
    }

    @Test
    void permit() {
        boolean b = stream(PERMIT).allMatch( it -> Validators.mustContainMinimum(it, MIN, ALPHA_LOWERCASE) );
        assertTrue(b);
    }

}
