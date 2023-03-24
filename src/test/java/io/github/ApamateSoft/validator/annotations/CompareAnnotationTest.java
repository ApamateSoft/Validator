package io.github.ApamateSoft.validator.annotations;


import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import org.junit.jupiter.api.Test;

import static io.github.ApamateSoft.validator.utils.Constants.*;

public class CompareAnnotationTest {

//    @Required
//    @MinLength(min = 12)
//    @MustContainMin(min = 3, condition = ALPHA_LOWERCASE)
//    @MustContainMin(min = 3, condition = ALPHA_UPPERCASE)
//    @MustContainMin(min = 3, condition = NUMBER)
//    @MustContainMin(min = 3, condition = "@~_/")
//    @MustContainOne(condition = ALPHA_LOWERCASE)
//    @MustContainOne(condition = ALPHA_UPPERCASE)
//    @MustContainOne(condition = NUMBER)
//    @MustContainOne(condition = "@~_/")
    @NotContain(condition = ALPHA_LOWERCASE)
    @NotContain(condition = ALPHA_UPPERCASE)
    @NotContain(condition = NUMBER)
    @NotContain(condition = "@~_/")
    @Compare(compareWith = "passwordConfirm")
    private String password = "-";

    private String passwordConfirm = "abcABC___111111111111";

    @Test
    void test() {
        try {
            Validator.validOrFail(this);
        } catch (InvalidEvaluationException e) {
            System.out.println(">>: e: "+e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
