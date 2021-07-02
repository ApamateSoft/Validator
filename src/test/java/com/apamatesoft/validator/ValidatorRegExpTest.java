package com.apamatesoft.validator;

import com.apamatesoft.validator.exceptions.InvalidEvaluationException;
import com.apamatesoft.validator.functions.NotPass;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.regExp;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ValidatorRegExpTest {

    private final static String REG_EXP = "^abc$";
    private final static NotPass notPass = mock(NotPass.class);
    private final static Validator validator = new Validator();
    private final static Validator validatorBuild = new Validator.Builder()
            .regExp(REG_EXP)
            .build();

    @BeforeAll
    static void beforeAll() {
        validator.regExp(REG_EXP);
    }

    @Test
    void returnFalseForNullValue() {
        assertFalse(regExp(null, REG_EXP));
    }

    @Test
    void ExceptionIsExpectedIfTextIsEmpty() {
        assertThrows(InvalidEvaluationException.class, () -> validator.isValidOrFail(null) );
    }

    @Test
    void verifyCallback() {
        validator.onNotPass(notPass);
        validator.isValid(null);
        verify(notPass).invoke("Value does not match pattern ^abc$");
    }

    @Test
    void verifyCallback_build() {
        validatorBuild.onNotPass(notPass);
        validatorBuild.isValid(null);
        verify(notPass).invoke("Value does not match pattern ^abc$");
    }

}
