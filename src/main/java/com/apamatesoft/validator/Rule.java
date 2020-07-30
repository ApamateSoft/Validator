package com.apamatesoft.validator;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

public class Rule {

    private final String message;
    private final Validate validate;

    public Rule(@NotNull final String message, @NotNull final Validate validate) {
        this.validate = validate;
        this.message = message;
    }

    public boolean validate(@Nullable final String evaluate) {
        return validate.invoke(evaluate);
    }

    public String getMessage() {
        return message;
    }

}
