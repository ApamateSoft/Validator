package com.apamatesoft.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Validator
 *
 * @author ApamateSoft
 * @version 0.0.4
 */
public class Validator implements Cloneable {

    public static final String ALPHABET = "aAbBcCdDeEfFgGhHiIjJkKlLmMnNñÑoOpPqQrRsStTuUvVwWxXyYzZ";
    public static final String ALPHA_LOWERCASE = "abcdefghijklmnñopqrstuvwxyz";
    public static final String ALPHA_UPPERCASE = "ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";
    public static final String ALPHA_NUMERIC = "0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNñÑoOpPqQrRsStTuUvVwWxXyYzZ";
    public static final String ALPHA_NUMERIC_LOWERCASE = "0123456789abcdefghijklmnñopqrstuvwxyz";
    public static final String ALPHA_NUMERIC_UPPERCASE = "0123456789ABCDEFGHIJKLMNÑOPQRSTUVWXYZ";

    private final List<Rule> rules = new ArrayList<>();
    private NotPass notPass;
    private String notMathMessage;

    public boolean isCompare() {
        return notMathMessage!=null;
    }

    /**
     * Evalúa sobre el String dado, todas las reglas definidas previamente en este objeto.
     * @param evaluate String a evaluar.
     * @return true: si pasa la validación, <br>
     *         false: si no pasa la validación.
     */
    public boolean validate(String evaluate) {
        for (Rule rule: rules) {
            if ( !rule.validate( evaluate ) ) {
                if (notPass!=null) notPass.action( rule.message );
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
     * {@link #match(String message)} al momento de definir el Objeto Validator.
     * @param evaluate String a evaluar.
     * @param compare String a comparar.
     * @return true: si pasa la validación, <br>
     *         false: si no pasa la validación.
     */
    public boolean compare(String evaluate, String compare) {
        if (evaluate==null || compare==null) {
            if (notPass!=null) notPass.action( notMathMessage );
            return false;
        }
        final boolean math = evaluate.equals( compare );
        if (!math) {
            if (notPass!=null) notPass.action( notMathMessage );
            return false;
        }
        return validate(evaluate);
    }

    /**
     * Define el mensaje de error en caso de fallar la validación match que compara el String a evaluar con otro.
     * @param message Mensaje de error en caso de fallar la validación match.
     * @return Validator.
     */
    public Validator match(String message) {
        this.notMathMessage = message;
        return this;
    }

    //<editor-fold desc="RULES">
    /**
     * Método que permite agregar reglas personalizadas.
     * <br><br>
     * <b>Ejemplo:</b><br>
     * <code>
     * <pre>
     * new Validator()
     *     .rule( evaluate -> {
     *         return evaluate.equals("ejemplo");
     *     },
     *     "El texto es diferente de ejemplo");
     * </pre>
     * </code>
     *
     * @param validate Expresión lambda con la cóndicion a cumplir el String a evaluar para ser considerado valido.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator rule(Validate validate, String message) {
        rules.add( new Rule(validate, message) );
        return this;
    }

    // REGLAS DE LONGITUD //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Verifica que el String a evaluar sea diferente de un String vaciío o de null.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator notEmpty(String message) {
        final Validate validate = evaluate -> ( evaluate!=null && !evaluate.isEmpty() );
        return rule(validate, message);
    }

    /**
     * Verifica que el String a evaluar tenga una longitud exacta de caracteres a la condición dada.
     * @param condition longitud de caracteres que debe tener el String a evaluar.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator length(int condition, String message) {
        message = String.format(message, condition);
        final Validate validate = evaluate -> evaluate.length()==condition;
        return rule(validate, message);
    }

    /**
     * Verifica que el String a evaluar tenga una longitud de caracteres minima a la condición dada.
     * @param condition Longitud de caracteres minima a cumplir el String a evaluar.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator minLength(int condition, String message) {
        message = String.format(message, condition);
        final Validate validate = evaluate -> evaluate.length()>=condition;
        return rule(validate, message);
    }

    /**
     * Verifica que el String a evaluar tenga una longitud maxima de caracteres a la condición dada.
     * @param condition longitud maxima de caracteres a cumplir el String a evaluar.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator maxLength(int condition, String message) {
        message = String.format(message, condition);
        final Validate validate = evaluate -> evaluate.length()<=condition;
        return rule(validate, message);
    }

    // REGLAS DE FORMATO ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Verifica que el String a evaluar tenga un formato de correo electónico (email)
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator isEmail(String message) {
        final String RE = "^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$";
        final Pattern pattern = Pattern.compile(RE);
        final Validate validate = evaluate -> pattern.matcher(evaluate).find();
        return rule(validate, message);
    }

    /**
     * Verifica que el String a evaluar tenga un formato numérico.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator isNumber(String message) {
        final Validate validate = evaluate -> {
            try {
                Double.parseDouble(evaluate);
                return true;
            } catch (Exception e) {
                return false;
            }
        };
        return rule(validate, message);
    }

    // REGLA DE CONTENIDO //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Verifica que el String a evaluar solo contenga caracteres incluidos en el String de condición.
     * @param condition String conformado por los caracteres validos.
     * @param message  Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator shouldOnlyContain(String condition, String message) {
        message = String.format(message, condition);
        final char[] evaluateChars = condition.toCharArray();
        final Validate validate = evaluate -> {
            for (char a: evaluateChars) {
                if ( !condition.contains( String.valueOf( a ) ) ) return false;
            }
            return true;
        };
        return rule(validate, message);
    }

    /**
     * Verifica que solo halla caracteres numéricos en el String a evaluar.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator onlyNumber(String message) {
        final Validate validate = evaluate -> {
            try {
                Integer.parseInt(evaluate);
                return true;
            } catch (Exception e) {
                return false;
            }
        };
        return rule(validate, message);
    }

    /**
     * Verifica que el String a evaluar no contenga algunos de los caracteres incluido en el String de la condición.
     * @param condition String que contiene los caracteres no deseados.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator notContain(String condition, String message) {
        message = String.format(message, condition);
        final char[] conditions = condition.toCharArray();
        final Validate validate = evaluate -> {
            for (char a: conditions) {
                if ( evaluate.contains( a+"" ) ) return false;
            }
            return true;
        };
        return rule(validate, message);
    }

    /**
     * Verifica que el String a evaluar contenga al menos uno de los caracteres incluidos en el String de la condición.
     * @param condition String que contiene caracteres deseados.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator mustContainOne(String condition, String message) {
        message = String.format(message, condition);
        final char[] conditions = condition.toCharArray();
        final Validate validate = evaluate -> {
            for (char a: conditions) {
                if ( evaluate.contains( a+"" ) ) return true;
            }
            return false;
        };
        return rule(validate, message);
    }
    //</editor-fold>

    /**
     * Evento que se dispara en caso de no cumplirse alguna de las reglas definida en este Objeto.
     * @param notPass Función a ejecutar con el mensaje de error de la regla no cumplida.
     */
    public void notPass(NotPass notPass) {
        this.notPass = notPass;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    class Rule {

        private String message;
        private Validate validate;

        public Rule(Validate validate, String message) {
            this.validate = validate;
            this.message = message;
        }

        public boolean validate(String evaluate) {
            return validate.action( evaluate );
        }

    }

    @FunctionalInterface
    public interface Validate {
        boolean action(String evaluate);
    }

    @FunctionalInterface
    public interface NotPass {
        void action(String message);
    }

}