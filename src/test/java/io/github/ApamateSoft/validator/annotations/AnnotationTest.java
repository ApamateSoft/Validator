package io.github.ApamateSoft.validator.annotations;

import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import org.junit.jupiter.api.Test;

public class AnnotationTest {

    @Test
    void test() {
        Login login = new Login();
        login.submit();
    }

}

class Login {

    @Required()
    @MinLength(min = 5)
    private String email = "email test";

    @Required()
    private String password = "password test";

    protected void submit() {
        email = "12345";
        try {
            Validator.validOrFail(this);
        } catch (InvalidEvaluationException e) {
            System.out.println(">>: error.message: "+e.getMessage());
            System.out.println(">>: error.value: "+e.getValue());
            throw new RuntimeException(e);
        }
    }

}
