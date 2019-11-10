package com.apamatesoft.validator;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author ApamateSoft
 * @version 0.0.1
 */
public class Validator {

    private final List<Rule> rules = new ArrayList<>();
    private NotPass notPass;
    private String notMathMessage;

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
    public boolean match(String evaluate, String compare) {
        final boolean math = evaluate.equals( compare );
        if (!math) {
            if (notPass!=null) notPass.action( notMathMessage );
            return false;
        }
        return validate(evaluate);
    }

    public Validator rule(Validate validate, String message) {
        rules.add( new Rule(validate, message) );
        return this;
    }

    //<editor-fold defaultstate="collapsed" desc="RULES">

    /**
     * Define el mensaje de error en caso de fallar la validación match que compara el String a evaluar con otro.
     * @param message Mensaje de error en caso de fallar la validación match.
     * @return Validator.
     */
    public Validator match(String message) {
        this.notMathMessage = message;
        return this;
    }

    /**
     * Regla que impide que el String a evaluar este vacío, también aplica en caso de null.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator notEmpty(String message) {
        final Validate validate = evaluate -> ( evaluate!=null && !evaluate.isEmpty() );
        return rule(validate, message);
    }

    /**
     * Regla que impide que el String a evaluar tenga una longitud de caracteres diferente a la condición dada.
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
     * Regla que impide que el String a evaluar tenga una longitud de caracteres menor a la condición dada.
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
     * Regla que impide que el String a evaluar tenga una longitud de caracteres mayor a la condición dada.
     * @param condition longitud maxima de caracteres a cumplir el String a evaluar.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator maxLength(int condition, String message) {
        message = String.format(message, condition);
        final Validate validate = evaluate -> evaluate.length()<=condition;
        return rule(validate, message);
    }

    /**
     * Regla que impide que el String tenga un formato diferente al de un correo electrónico.
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
     * Regla que impide que el String a evaluar tenga caracteres diferentes a caracteres numéricos.
     * @param message Mensaje de error para esta regla.
     * @return Validator.
     */
    public Validator onlyNumber(String message) {
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


    public Validator onlyCharacters(String message) {
        final Validate validate = evaluate -> true;
        return rule(validate, message);
    }

    /**
     * Regla que impide que el String a evaluar contenga al menos uno de los caracteres incluido en el String de la
     * condición.
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
     * Regla que verifica que el String a evaluar contenga al menos uno de los caracteres incluidos en el String de la
     * condición.
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

    public void notPass(NotPass notPass) {
        this.notPass = notPass;
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
    interface Validate {
        boolean action(String evaluate);
    }

    @FunctionalInterface
    interface NotPass {
        void action(String message);
    }

}