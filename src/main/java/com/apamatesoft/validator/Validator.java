// TODO:
//  - Crear reglas para numeros > < =
//  - Regla de fecha
//  - Crear readme
//  - Traducir
package com.apamatesoft.validator;

import com.apamatesoft.validator.messages.Messages;
import com.apamatesoft.validator.messages.MessagesEn;
import com.apamatesoft.validator.functions.NotPass;
import com.apamatesoft.validator.functions.Validate;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.apamatesoft.validator.constants.Constants.EMAIL_RE;
import static com.apamatesoft.validator.constants.Constants.NUMBER;

/**
 * Validator
 *
 * @author ApamateSoft
 * @version 2.0.0
 */
public class Validator implements Cloneable {

    private static Messages messages = new MessagesEn();

    private final List<Rule> rules = new ArrayList<>();
    private NotPass notPass;
    private String notMatchMessage = messages.getNotMatchMessage();

    // <editor-fold defaultstate="collapsed" desc="CONSTRUCTORS">
    public Validator() { }

    private Validator(Builder builder) {
        rules.addAll(builder.rules);
        notPass = builder.notPass;
        notMatchMessage = builder.notMatchMessage;
    }
    //</editor-fold>

    /**
     * Establece los mensajes de error predeterminados para cada una de las reglas pre establecidas.
     * @param messages mensajes de error.
     */
    public static void setMessages(Messages messages) {
        if (messages==null) return;
        Validator.messages = messages;
    }

    /**
     * Establece el mensaje de error a mostrar, en caso de que la comparación de los String falle en el método
     * {@link #compare(String, String)}.
     * @param message Mensaje de error.
     */
    public void setNotMatchMessage(String message) {
        this.notMatchMessage = message;
    }

    /**
     * Valida que el String a evaluar cumpla todas las reglas.<br>
     * <b>Nota:</b> Si el String no cumple alguna regla, se invocara al evento {@link #notPass(NotPass)} con el mensaje
     * del error correspondiente.
     * @param evaluate String a evaluar.
     * @return true: si pasa la validación.
     */
    public boolean validate(String evaluate) {
        for (Rule rule: rules) {
            if (!rule.validate(evaluate)) {
                if (notPass!=null) notPass.invoke(rule.getMessage());
                return false;
            }
        }
        return true;
    }

    /**
     * Valida que ambos String coincidan y que cumplan todas las reglas.<br>
     * <b>Nota:</b> Si los Strings no cumplen con alguna regla, se invocara al evento {@link #notPass(NotPass)}, con el
     * mensaje del error correspondiente. con el método {@link #setNotMatchMessage(String)} se establece un mensaje de
     * error en caso de que la comparación falle.
     * @param evaluate String a evaluar.
     * @param compare String a comparar.
     * @return true: si pasa la validación.clas
     */
    public boolean compare(String evaluate, String compare) {
        if (evaluate==null || compare==null) {
            if (notPass!=null) notPass.invoke(notMatchMessage);
            return false;
        }
        if (!evaluate.equals(compare)) {
            if (notPass!=null) notPass.invoke(notMatchMessage);
            return false;
        }
        return validate(evaluate);
    }

    //<editor-fold desc="RULES">
    /**
     * Crea una regla de validación.
     * <br><br>
     * <b>Ejemplo:<b/><br>
     * <code>
     * <pre>
     * new Validator().rule("El texto es diferente de 'xxx'", evaluate -> {
     *     return evaluate.equals("xxx");
     * });
     * </pre>
     * </code>
     *
     * @param message Mensaje de error.
     * @param validate Función que retorna true cuando el String a evaluar cumpla las condiciones.
     */
    public void rule(String message, Validate validate) {
        rules.add(new Rule(message, validate));
    }

    // REGLAS DE LONGITUD //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida que el String a evaluar sea diferente de un vacío y null.
     * @param message Mensaje de error.
     */
    public void required(String message) {
        rule(message, it -> it!=null && !it.isEmpty());
    }

    /**
     * Valida que el String a evaluar sea diferente de un vacío y null.
     */
    public void required() {
        required(messages.getRequireMessage());
    }

    /**
     * Valida que el String a evaluar tenga la longitud exacta de carácteres a la condición.
     * @param condition longitud de caracteres.
     * @param message Mensaje de error.
     */
    public void length(int condition, String message) {
        rule(String.format(message, condition), it -> it.length()==condition);
    }

    /**
     * Valida que el String a evaluar tenga la longitud exacta de carácteres a la condición.
     * @param condition longitud de caracteres.
     */
    public void length(int condition) {
        length(condition, messages.getLengthMessage());
    }

    /**
     * Valida que el String a evaluar tenga una longitud de caracteres minima a la condición.
     * @param condition Longitud minima de carácteres.
     * @param message Mensaje de error.
     */
    public void minLength(int condition, String message) {
        rule(String.format(message, condition), it -> it.length()>=condition);
    }

    /**
     * Valida que el String a evaluar tenga una longitud de caracteres minima a la condición.
     * @param condition Longitud minima de carácteres.
     */
    public void minLength(int condition) {
        minLength(condition, messages.getMinLengthMessage());
    }

    /**
     * Valida que el String a evaluar tenga una longitud maxima de carácteres a la condición.
     * @param condition longitud maxima de carácteres.
     * @param message Mensaje de error.
     */
    public void maxLength(int condition, String message) {
        rule(String.format(message, condition), it -> it.length()<=condition);
    }

    /**
     * Valida que el String a evaluar tenga una longitud maxima de carácteres a la condición.
     * @param condition longitud maxima de carácteres.
     */
    public void maxLength(int condition) {
        maxLength(condition, messages.getMaxLengthMessage());
    }

    // REGLAS DE FORMATO ///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida que el String a evaluar tenga un formato de email
     * @param message Mensaje de error.
     */
    public void email(String message) {
        rule(message, it -> Pattern.compile(EMAIL_RE).matcher(it).find());
    }

    /**
     * Valida que el String a evaluar tenga un formato de email
     */
    public void email() {
        email(messages.getEmailMessage());
    }

    /**
     * Valida que el String a evaluar tenga un formato numérico.
     * @param message Mensaje de error.
     */
    public void numericFormat(String message) {
        rule(message, it -> {
            try {
                double number = Double.parseDouble(it);
                return !Double.isNaN(number);
            } catch (Exception e) {
                return false;
            }
        });
    }

    /**
     * Valida que el String a evaluar tenga un formato numérico.
     */
    public void numericFormat() {
        numericFormat(messages.getNumericFormat());
    }

    // REGLA DE CONTENIDO //////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * Valida que el String a evaluar solo contenga carácteres incluidos en el String de condición.
     * @param condition String con carácteres permitidos.
     * @param message  Mensaje de error.
     */
    public void shouldOnlyContain(String condition, String message) {
        rule(String.format(message, condition), it -> {
            for (char a: it.toCharArray()) {
                if (!condition.contains(String.valueOf(a))) return false;
            }
            return true;
        });
    }

    /**
     * Valida que el String a evaluar solo contenga carácteres incluidos en el String de condición.
     * @param condition String con carácteres permitidos.
     */
    public void shouldOnlyContain(String condition) {
        shouldOnlyContain(condition, messages.getShouldOnlyContainMessage());
    }

    /**
     * Valida que el Staring a evaluar solo contenga carácteres numéricos.
     * @param message Mensaje de error.
     */
    public void onlyNumbers(String message) {
        shouldOnlyContain(NUMBER, message);
    }

    /**
     * Valida que el Staring a evaluar solo contenga carácteres numéricos.
     */
    public void onlyNumbers() {
        onlyNumbers(messages.getOnlyNumbersMessage());
    }

    /**
     * Valida que el String a evaluar no contenga algún carácter incluido en el String de la condición.
     * @param condition String con caracteres no válidos.
     * @param message Mensaje de error.
     */
    public void notContain(String condition, String message) {
        rule(String.format(message, condition), it -> {
            for (char a: condition.toCharArray()) {
                if (it.contains(a+"")) return false;
            }
            return true;
        });
    }

    /**
     * Valida que el String a evaluar no contenga algún carácter incluido en el String de la condición.
     * @param condition String con caracteres no válidos.
     */
    public void notContain(String condition) {
        notContain(condition, messages.getNotContainMessage());
    }

    /**
     * Valida que el String a evaluar contenga al menos un carácter incluido en el String de la condición.
     * @param condition String con caracteres deseados.
     * @param message Mensaje de error.
     */
    public void mustContainOne(String condition, String message) {
        rule(String.format(message, condition), it -> {
            for (char a: condition.toCharArray()) {
                if (it.contains(a+"")) return true;
            }
            return false;
        });
    }

    /**
     * Valida que el String a evaluar contenga al menos un carácter incluido en el String de la condición.
     * @param condition String con caracteres deseados.
     */
    public void mustContainOne(String condition) {
        mustContainOne(condition, messages.getMustContainOneMessage());
    }
    //</editor-fold>

    /**
     * Evento que se invoca al no cumplirse alguna regla.
     * @param notPass Función con el mensaje de error.
     */
    public void notPass(NotPass notPass) {
        this.notPass = notPass;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    /**
     * Crea una copia del objeto Validator.<br>
     * @return copia de Validator.
     */
    public Validator copy() {
        try {
            return (Validator) this.clone();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Clase que permite construir un Validator de forma secuencial y centralizada.
     */
    public static class Builder {

        private List<Rule> rules = new ArrayList<>();
        private NotPass notPass;
        private String notMatchMessage = messages.getNotMatchMessage();

        /**
         * Establece el mensaje de error a mostrar, en caso de que la comparación de los String falle en el método
         * {@link #compare(String, String)}.
         * @param message Mensaje de error.
         * @return Bulder
         */
        public Builder setNotMatchMessage(String message) {
            this.notMatchMessage = message;
            return this;
        }

        //<editor-fold desc="RULES">

        /**
         * Crea una regla de validación.
         * <br><br>
         * <b>Ejemplo:<b/><br>
         * <code>
         * <pre>
         * new Validator().rule("El texto es diferente de ejemplo", evaluate -> {
         *     return evaluate.equals("ejemplo");
         * });
         * </pre>
         * </code>
         *
         * @param message Mensaje de error.
         * @param validate Función que retorna true cuando el String a evaluar cumpla las condiciones.
         * @return Bulder
         */
        public Builder rule(String message, Validate validate) {
            rules.add(new Rule(message, validate));
            return this;
        }

        /**
         * Valida que el String a evaluar sea diferente de un vacío y null.
         * @param message Mensaje de error.
         * @return Bulder
         */
        public Builder required(String message) {
            return rule(message, it -> it!=null && !it.isEmpty());
        }

        /**
         * Valida que el String a evaluar sea diferente de un vacío y null.
         * @return Bulder
         */
        public Builder required() {
            return required(messages.getRequireMessage());
        }

        /**
         * Valida que el String a evaluar tenga la longitud exacta de carácteres a la condición.
         * @param condition longitud de caracteres.
         * @param message Mensaje de error.
         * @return Bulder
         */
        public Builder length(int condition, String message) {
            return rule(String.format(message, condition), it -> it.length()==condition);
        }

        /**
         * Valida que el String a evaluar tenga la longitud exacta de carácteres a la condición.
         * @param condition longitud de caracteres.
         * @return Bulder
         */
        public Builder length(int condition) {
            return length(condition, messages.getLengthMessage());
        }

        /**
         * Valida que el String a evaluar tenga una longitud de caracteres minima a la condición.
         * @param condition Longitud minima de carácteres.
         * @param message Mensaje de error.
         * @return Builder
         */
        public Builder minLength(int condition, String message) {
            return rule(String.format(message, condition), it -> it.length()>=condition);
        }

        /**
         * Valida que el String a evaluar tenga una longitud de caracteres minima a la condición.
         * @param condition Longitud minima de carácteres.
         * @return Builder
         */
        public Builder minLength(int condition) {
            return minLength(condition, messages.getMinLengthMessage());
        }

        /**
         * Valida que el String a evaluar tenga una longitud maxima de carácteres a la condición.
         * @param condition longitud maxima de carácteres.
         * @param message Mensaje de error.
         * @return Builder
         */
        public Builder maxLength(int condition, String message) {
            return rule(String.format(message, condition), it -> it.length()<=condition);
        }

        /**
         * Valida que el String a evaluar tenga una longitud maxima de carácteres a la condición.
         * @param condition longitud maxima de carácteres.
         * @return Builder
         */
        public Builder maxLength(int condition) {
            return maxLength(condition, messages.getMaxLengthMessage());
        }

        /**
         * Valida que el String a evaluar tenga un formato de email
         * @param message Mensaje de error.
         * @return Builder
         */
        public Builder email(String message) {
            return rule(message, it -> Pattern.compile(EMAIL_RE).matcher(it).find());
        }

        /**
         * Valida que el String a evaluar tenga un formato de email
         * @return Builder
         */
        public Builder email() {
            return email(messages.getEmailMessage());
        }

        /**
         * Valida que el String a evaluar tenga un formato numérico.
         * @param message Mensaje de error.
         * @return Builder
         */
        public Builder numericFormat(String message) {
            return rule(message, it -> {
                try {
                    double number = Double.parseDouble(it);
                    return !Double.isNaN(number);
                } catch (Exception e) {
                    return false;
                }
            });
        }

        /**
         * Valida que el String a evaluar tenga un formato numérico.
         * @return Builder
         */
        public Builder numericFormat() {
            return numericFormat(messages.getNumericFormat());
        }

        /**
         * Valida que el String a evaluar solo contenga carácteres incluidos en el String de condición.
         * @param condition String con carácteres permitidos.
         * @param message  Mensaje de error.
         * @return Builder
         */
        public Builder shouldOnlyContain(String condition, String message) {
            return rule(String.format(message, condition), it -> {
                for (char a: it.toCharArray()) {
                    if (!condition.contains(String.valueOf(a))) return false;
                }
                return true;
            });
        }

        /**
         * Valida que el String a evaluar solo contenga carácteres incluidos en el String de condición.
         * @param condition String con carácteres permitidos.
         * @return Builder
         */
        public Builder shouldOnlyContain(String condition) {
            return shouldOnlyContain(condition, messages.getShouldOnlyContainMessage());
        }

        /**
         * Valida que el Staring a evaluar solo contenga carácteres numéricos.
         * @param message Mensaje de error.
         * @return Builder
         */
        public Builder onlyNumbers(String message) {
            return shouldOnlyContain(NUMBER, message);
        }

        /**
         * Valida que el Staring a evaluar solo contenga carácteres numéricos.
         * @return Builder
         */
        public Builder onlyNumbers() {
            return onlyNumbers(messages.getOnlyNumbersMessage());
        }

        /**
         * Valida que el String a evaluar no contenga algún carácter incluido en el String de la condición.
         * @param condition String con caracteres no válidos.
         * @param message Mensaje de error.
         * @return Builder
         */
        public Builder notContain(String condition, String message) {
            return rule(String.format(message, condition), it -> {
                for (char a: condition.toCharArray()) {
                    if (it.contains(a+"")) return false;
                }
                return true;
            });
        }

        /**
         * Valida que el String a evaluar no contenga algún carácter incluido en el String de la condición.
         * @param condition String con caracteres no válidos.
         * @return Builder
         */
        public Builder notContain(String condition) {
            return notContain(condition, messages.getNotContainMessage());
        }

        /**
         * Valida que el String a evaluar contenga al menos un carácter incluido en el String de la condición.
         * @param condition String con caracteres deseados.
         * @param message Mensaje de error.
         * @return Builder
         */
        public Builder mustContainOne(String condition, String message) {
            return rule(String.format(message, condition), it -> {
                for (char a: condition.toCharArray()) {
                    if (it.contains(a+"")) return true;
                }
                return false;
            });
        }

        /**
         * Valida que el String a evaluar contenga al menos un carácter incluido en el String de la condición.
         * @param condition String con caracteres deseados.
         * @return Builder
         */
        public Builder mustContainOne(String condition) {
            return mustContainOne(condition, messages.getMustContainOneMessage());
        }
        //</editor-fold>

        /**
         * Evento que se invoca al no cumplirse alguna regla.
         * @param notPass Función con el mensaje de error.
         * @return Builder
         */
        public Builder notPass(NotPass notPass) {
            this.notPass = notPass;
            return this;
        }

        /**
         * Construye el Validator
         * @return Validator
         */
        public Validator build() {
            return new Validator(this);
        }

    }

}