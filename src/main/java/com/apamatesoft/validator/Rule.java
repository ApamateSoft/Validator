package com.apamatesoft.validator;

public class Rule {

    private final String message;
    private final Validate validate;

    public Rule(final String message, final Validate validate) {
        this.validate = validate;
        this.message = message;
    }

    public boolean validate(final String evaluate) {
        return validate.invoke(evaluate);
    }

    public String getMessage() {
        return message;
    }

}
