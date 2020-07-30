package com.apamatesoft.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validator
 *
 * @author ApamateSoft
 * @version 1.0.1
 */
public class Validator implements Cloneable {

    //<editor-fold defaultstate="collapsed" desc="TEMPLATES">
    public static final String NUMBER = "0123456789";

    public static final String ALPHABET = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ";
    public static final String NAME = ALPHABET+" ";
    public static final String ALPHA_LOWERCASE = "abcdefghijklmnopqrstuvwxyz";
    public static final String ALPHA_UPPERCASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String ALPHA_NUMERIC = NUMBER+ALPHABET;
    public static final String ALPHA_NUMERIC_LOWERCASE = NUMBER+ALPHA_LOWERCASE;
    public static final String ALPHA_NUMERIC_UPPERCASE = NUMBER+ALPHA_UPPERCASE;

    public static final String ALPHABET_ES = "aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚvVwWxXyYzZ";
    public static final String NAME_ES = ALPHABET_ES+" ";
    public static final String ALPHA_LOWERCASE_ES = "aábcdeéfghiíjklmnñoópqrstuúvwxyz";
    public static final String ALPHA_UPPERCASE_ES = "AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ";
    public static final String ALPHA_NUMERIC_ES = NUMBER+ALPHABET_ES;
    public static final String ALPHA_NUMERIC_LOWERCASE_ES = NUMBER+ALPHA_LOWERCASE_ES;
    public static final String ALPHA_NUMERIC_UPPERCASE_ES = NUMBER+ALPHA_UPPERCASE_ES;
    //</editor-fold>

    private final List<Rule> rules = new ArrayList<>();
    private NotPass notPass;
    private String notMathMessage;

    //<editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    public Validator() { }

    private Validator(final Builder builder) {
        rules.addAll(builder.rules);
        notPass = builder.notPass;
        notMathMessage = builder.notMathMessage;
    }
    //</editor-fold>

    /**
     * Evalúa sobre el String dado, todas las reglas definidas previamente en este objeto.
     * @param evaluate String a evaluar.
     * @return true: si pasa la validación <br>
     *         false: si no pasa la validación
     */
    public boolean validate(final String evaluate) {
        for (Rule rule: rules) {
            if (!rule.validate(evaluate)) {
                if (notPass!=null) notPass.invoke(rule.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * Evalúa que ambos String pasado como parámetros coincidan, de ser este el caso se procede a evaluar todas las
     * reglas definidas previamente en este objeto.
     * <br><br>
     * <b>Nota:</b> se recomienda definir el mensaje de error en caso que los String no coincidan, utilizando el método
     * {@link #setNotMathMessage(String message)} al momento de definir el Objeto Validator.
     * @param evaluate String a evaluar.
     * @param compare String a comparar.
     * @return true: si pasa la validación, <br>
     *         false: si no pasa la validación.
     */
    public boolean compare(final String evaluate, final String compare) {
        if (evaluate==null || compare==null) {
            if (notPass!=null) notPass.invoke(notMathMessage);
            return false;
        }
        if (!evaluate.equals(compare)) {
            if (notPass!=null) notPass.invoke(notMathMessage);
            return false;
        }
        return validate(evaluate);
    }

    /**
     * Define el mensaje de error en caso de fallar la validación match que compara el String a evaluar con otro.
     * @param message Mensaje de error en caso de fallar la validación match.
     */
    public void setNotMathMessage(final String message) {
        this.notMathMessage = message;
    }

    //<editor-fold desc="RULES">

    /**
     * Método que permite agregar reglas personalizadas.
     * <br><br>
     * Ejemplo:<br>
     * <code>
     * <pre>
     * new Validator()
     *     .rule("El texto es diferente de ejemplo", evaluate -> {
     *          return evaluate.equals("ejemplo");
     *      });
     * </pre>
     * </code>
     *
     * @param message Mensaje de error para esta regla.
     * @param validate Expresión lambda con la cóndicion a cumplir el String a evaluar para ser considerado valido.
     */
    public void rule(final String message, final Validate validate) {
        rules.add(new Rule(message, validate));
    }

    // REGLAS DE LONGITUD //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Verifica que el String a evaluar sea diferente de un String vacío o de null.
     * @param message Mensaje de error para esta regla.
     */
    public void required(final String message) {
        rule(message, it -> it!=null && !it.isEmpty());
    }

    /**
     * Verifica que el String a evaluar tenga una longitud exacta de caracteres a la condición dada.
     * @param condition longitud de caracteres que debe tener el String a evaluar.
     * @param message Mensaje de error para esta regla.
     */
    public void length(final int condition, final String message) {
        rule(String.format(message, condition), it -> it.length()==condition);
    }

    /**
     * Verifica que el String a evaluar tenga una longitud de caracteres minima a la condición dada.
     * @param condition Longitud de caracteres minima a cumplir el String a evaluar.
     * @param message Mensaje de error para esta regla.
     */
    public void minLength(final int condition, final String message) {
        rule(String.format(message, condition), it -> it.length()>=condition);
    }

    /**
     * Verifica que el String a evaluar tenga una longitud maxima de caracteres a la condición dada.
     * @param condition longitud maxima de caracteres a cumplir el String a evaluar.
     * @param message Mensaje de error para esta regla.
     */
    public void maxLength(final int condition, final String message) {
        rule(String.format(message, condition), it -> it.length()<=condition);
    }

    // REGLAS DE FORMATO ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Verifica que el String a evaluar tenga un formato de correo electónico (email)
     * @param message Mensaje de error para esta regla.
     */
    public void email(final String message) {
        final String RE = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        rule(message, it -> Pattern.compile(RE).matcher(it).find());
    }

    /**
     * Verifica que el String a evaluar tenga un formato numérico.
     * @param message Mensaje de error para esta regla.
     */
    public void number(final String message) {
        rule(message, it -> {
            try {
                final double number = Double.parseDouble(it);
                return !Double.isNaN(number);
            } catch (Exception e) {
                return false;
            }
        });
    }

    // REGLA DE CONTENIDO //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Verifica que el String a evaluar solo contenga caracteres incluidos en el String de condición.
     * @param condition String conformado por los caracteres validos.
     * @param message  Mensaje de error para esta regla.
     */
    public void shouldOnlyContain(final String condition, final String message) {
        rule(String.format(message, condition), it -> {
            for (char a: it.toCharArray()) {
                if (!condition.contains(String.valueOf(a))) return false;
            }
            return true;
        });
    }

    /**
     * Verifica que solo halla caracteres numéricos en el String a evaluar.
     * @param message Mensaje de error para esta regla.
     */
    public void onlyNumber(final String message) {
        shouldOnlyContain(NUMBER, message);
    }

    /**
     * Verifica que el String a evaluar no contenga algunos de los caracteres incluido en el String de la condición.
     * @param condition String que contiene los caracteres no deseados.
     * @param message Mensaje de error para esta regla.
     */
    public void notContain(final String condition, final String message) {
        rule(String.format(message, condition), it -> {
            for (char a: condition.toCharArray()) {
                if (it.contains(a+"")) return false;
            }
            return true;
        });
    }

    /**
     * Verifica que el String a evaluar contenga al menos uno de los caracteres incluidos en el String de la condición.
     * @param condition String que contiene caracteres deseados.
     * @param message Mensaje de error para esta regla.
     */
    public void mustContainOne(final String condition, final String message) {
        rule(String.format(message, condition), it -> {
            for (char a: condition.toCharArray()) {
                if (it.contains(a+"")) return true;
            }
            return false;
        });
    }
    //</editor-fold>

    /**
     * Evento que se dispara en caso de no cumplirse alguna de las reglas definida en este Objeto.
     * @param notPass Función a ejecutar con el mensaje de error de la regla no cumplida.
     */
    public void notPass(final NotPass notPass) {
        this.notPass = notPass;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public Validator copy() {
        try {
            return (Validator) this.clone();
        } catch (Exception e) {
            return null;
        }
    }

    public static class Builder {

        private final List<Rule> rules = new ArrayList<>();
        private NotPass notPass;
        private String notMathMessage;

        /**
         * Define el mensaje de error en caso de fallar la validación match que compara el String a evaluar con otro.
         * @param message Mensaje de error en caso de fallar la validación match.
         */
        public Builder setNotMathMessage(final String message) {
            this.notMathMessage = message;
            return this;
        }

        //<editor-fold desc="RULES">

        /**
         * Método que permite agregar reglas personalizadas.
         * <br><br>
         * Ejemplo:<br>
         * <code>
         * <pre>
         * new Validator()
         *     .rule("El texto es diferente de ejemplo", evaluate -> {
         *          return evaluate.equals("ejemplo");
         *      });
         * </pre>
         * </code>
         *
         * @param message Mensaje de error para esta regla.
         * @param validate Expresión lambda con la cóndicion a cumplir el String a evaluar para ser considerado valido.
         */
        public Builder rule(final String message, final Validate validate) {
            rules.add(new Rule(message, validate));
            return this;
        }

        // REGLAS DE LONGITUD //////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Verifica que el String a evaluar sea diferente de un String vacío o de null.
         * @param message Mensaje de error para esta regla.
         */
        public Builder required(final String message) {
            return rule(message, it -> it!=null && !it.isEmpty());
        }

        /**
         * Verifica que el String a evaluar tenga una longitud exacta de caracteres a la condición dada.
         * @param condition longitud de caracteres que debe tener el String a evaluar.
         * @param message Mensaje de error para esta regla.
         */
        public Builder length(final int condition, final String message) {
            return rule(String.format(message, condition), it -> it.length()==condition);
        }

        /**
         * Verifica que el String a evaluar tenga una longitud de caracteres minima a la condición dada.
         * @param condition Longitud de caracteres minima a cumplir el String a evaluar.
         * @param message Mensaje de error para esta regla.
         */
        public Builder minLength(final int condition, final String message) {
            return rule(String.format(message, condition), it -> it.length()>=condition);
        }

        /**
         * Verifica que el String a evaluar tenga una longitud maxima de caracteres a la condición dada.
         * @param condition longitud maxima de caracteres a cumplir el String a evaluar.
         * @param message Mensaje de error para esta regla.
         */
        public Builder maxLength(final int condition, final String message) {
            return rule(String.format(message, condition), it -> it.length()<=condition);
        }

        // REGLAS DE FORMATO ///////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Verifica que el String a evaluar tenga un formato de correo electónico (email)
         * @param message Mensaje de error para esta regla.
         */
        public Builder email(final String message) {
            final String RE = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
            return rule(message, it -> Pattern.compile(RE).matcher(it).find());
        }

        /**
         * Verifica que el String a evaluar tenga un formato numérico.
         * @param message Mensaje de error para esta regla.
         */
        public Builder number(final String message) {
            return rule(message, it -> {
                try {
                    final double number = Double.parseDouble(it);
                    return !Double.isNaN(number);
                } catch (Exception e) {
                    return false;
                }
            });
        }

        // REGLA DE CONTENIDO //////////////////////////////////////////////////////////////////////////////////////////////

        /**
         * Verifica que el String a evaluar solo contenga caracteres incluidos en el String de condición.
         * @param condition String conformado por los caracteres validos.
         * @param message  Mensaje de error para esta regla.
         */
        public Builder shouldOnlyContain(final String condition, final String message) {
            return rule(String.format(message, condition), it -> {
                for (char a: it.toCharArray()) {
                    if (!condition.contains(String.valueOf(a))) return false;
                }
                return true;
            });
        }

        /**
         * Verifica que solo halla caracteres numéricos en el String a evaluar.
         * @param message Mensaje de error para esta regla.
         */
        public Builder onlyNumber(final String message) {
            return shouldOnlyContain(NUMBER, message);
        }

        /**
         * Verifica que el String a evaluar no contenga algunos de los caracteres incluido en el String de la condición.
         * @param condition String que contiene los caracteres no deseados.
         * @param message Mensaje de error para esta regla.
         */
        public Builder notContain(final String condition, final String message) {
            return rule(String.format(message, condition), it -> {
                for (char a: condition.toCharArray()) {
                    if (it.contains(a+"")) return false;
                }
                return true;
            });
        }

        /**
         * Verifica que el String a evaluar contenga al menos uno de los caracteres incluidos en el String de la condición.
         * @param condition String que contiene caracteres deseados.
         * @param message Mensaje de error para esta regla.
         */
        public Builder mustContainOne(final String condition, final String message) {
            return rule(String.format(message, condition), it -> {
                for (char a: condition.toCharArray()) {
                    if (it.contains(a+"")) return true;
                }
                return false;
            });
        }
        //</editor-fold>

        /**
         * Evento que se dispara en caso de no cumplirse alguna de las reglas definida en este Objeto.
         * @param notPass Función a ejecutar con el mensaje de error de la regla no cumplida.
         */
        public Builder notPass(final NotPass notPass) {
            this.notPass = notPass;
            return this;
        }

        public Validator build() {
            return new Validator(this);
        }

    }

}
