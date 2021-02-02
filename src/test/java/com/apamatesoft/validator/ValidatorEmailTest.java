package com.apamatesoft.validator;

import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

public class ValidatorEmailTest {

    private final static Validator validator = new Validator();

    @BeforeAll
    static void beforeAll() {
        validator.email();
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(validator.isValid(null));
    }

    @Test
    void returnFalseForStringWithDifferentEmailFormat() {
        assertFalse(validator.isValid("xxx"));
    }

    @Test
    void returnTrueForAnEmailFormattedString() {
        assertTrue(validator.isValid("example@mail.com"));
    }

    @Test
    void verifyCallback() {
        final NotPass notPass = mock(NotPass.class);
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("Email invalid");
    }

    private void a() {

        Validator validator = new Validator.Builder()
                .rule("Ingrese un texto diferente de null", Objects::nonNull)
                .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
                .build();

        // Solo se ejecuta si falla la validación de alguna regla
        validator.onNotPass( message -> System.out.println(message) );

        validator.isValid("yyy"); // return false

        // 1º regla: solo se aprobara si el String a evaluar es difefente de null, en caso contrario mostrara el mensaje
        // "Ingrese un texto diferente de null"
        validator.rule("Ingrese un texto diferente de null", (String evaluate) -> {
            return evaluate!=null;
        } );

        // 2º regla: solo se aprobara si el String a evaluar es igual a "xxx", en caso contrario mostrara el mensaje
        // "El texto es diferente de 'xxx'"
        validator.rule("El texto es diferente de 'xxx'", (String evaluate) -> {
            return evaluate.equals("xxx");
        } );

    }

}
