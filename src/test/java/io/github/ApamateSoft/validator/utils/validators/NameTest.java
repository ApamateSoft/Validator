package io.github.ApamateSoft.validator.utils.validators;

import io.github.ApamateSoft.validator.utils.Validators;
import org.junit.jupiter.api.Test;

import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NameTest {

    private static final String[] NOT_PERMIT = { null, "", "10", "1jose", "@omar", "Jesús", "jesus 1alberto", " jesus", "jesus " };
    private static final String[] PERMIT = { "jesus", "maria", "JOSE", "jesus maria", "Maria Jose", "Jose Jesus", "maria de jose" };

    @Test
    void notPermit() {
        assertFalse(stream(NOT_PERMIT).anyMatch(Validators::name));
    }

    @Test
    void permit() {
        assertTrue(stream(PERMIT).allMatch( it -> {
            boolean b = Validators.name(it);
            if (!b) System.out.println(it);
            return b;
        } ));
    }

}
