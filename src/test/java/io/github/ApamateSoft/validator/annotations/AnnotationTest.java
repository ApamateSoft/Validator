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
    private String email = "email test";
    public int age = 5;
    @Required()
    private String password = "password test";

    protected void submit() {
        email = "";
        try {
            Validator.validOrFail(this);
        } catch (InvalidEvaluationException e) {
            System.out.println(">>: error.message: "+e.getMessage());
            System.out.println(">>: error.value: "+e.getValue());
            throw new RuntimeException(e);
        }
    }

}
