# Validator

Validador es una librería escrita en Java, que pretende simplificar la validación de Strings declarando una series de 
reglas.

## Instalación
1. Descarga el Validator-1.0.0.jar.
2. Añade el .jar como una librería a tu proyecto.

## Empezando

```java
// Instanciando un nuevo validator
Validator validator = new Validator();

// 1º regla: solo se aprobara si el String a evaluar es difefente de null, en caso contrario mostrara el mensaje
// "Ingrese un texto diferente de null"
validator.rule("Ingrese un texto diferente de null", (String evaluate) -> {
    return evaluate!=null;
} );

// 2º regla: solo se aprobara si el String a evaluar es igual a "xxx", en caso contrario mostrara el mensaje
// "El texto es diferente de 'xxx'"
validator.rule("El texto es diferente de 'xxx'", (String evaluate) -> {
    return evaluate.equals("xxx");
} );
```

##### Nota:
- Se puede agregar n cantidad de reglas.
- Validator aplicara una a una las reglas, en el orden en que fueron agregadas.
- Al momento de fallar una regla, se ignoran las restantes. 
- Un String se considera valido, solo si este, pasa todas las reglas.

### Simplificando código

Puedes crear una instancia de Validator utilizando `.Builder()`.

```java
Validator validator = new Validator.Builder()
    .rule("Ingrese un texto diferente de null", Objects::nonNull)
    .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();
```

#### Validando un String

Se hace uso del método `.isValid` para saber si el String es válido.

```java
Validator validator = new Validator.Builder()
    .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();

validator.isValid("yyy"); // false
validator.isValid("xxx"); // true
```

En caso de querer comparar dos String, lo cual es muy útil para validar contraseñas, se puede hacer uso del método. 
`.compare`. Opcionalmente se puede utilizar el método `.setNotMatchMessage` para definir el mensaje de error en caso de 
no coincidir.  

```java
final Validator validator = new Validator.Builder()
    .rule("Requerido", evaluate -> !evaluate.isEmpty() )
    .setNotMatchMessage("No coinciden")
    .build();

validator.compare("abc", "xyz"); // false
validator.compare("abc", "abc"); // true
```

#### Capturando los mensajes de error
El evento `.onNotPass` se ejecuta al momento de fallar una regla al momento de su validación y devuelve su mensaje.

```java
Validator validator = new Validator.Builder()
    .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();

// Solo se ejecuta si falla la validación de alguna regla
validator.onNotPass( message -> System.out.println(message) ); // "El texto es diferente de 'xxx'"

validator.isValid("yyy"); // false
```

#### Reglas predefinidas

Validator ofrece una serie de reglas predefinidas.

| Regla	              | Condición     | Mensaje (`String`)  | Descripción                                                                                        |
|---------------------|---------------|---------------------|----------------------------------------------------------------------------------------------------|
| `required`	      | No            | Opcional            | Valida que el String a evaluar sea diferente de un vacío y null                                    |
| `length`	          | Si (`int`)    | Opcional            | Valida que el String a evaluar tenga la longitud exacta de caracteres a la condición               |
| `minLength`	      | Si (`int`)    | Opcional            | Valida que el String a evaluar tenga una longitud de caracteres minima a la condición              |
| `maxLength`	      | Si (`int`)    | Opcional            | Valida que el String a evaluar tenga una longitud maxima de caracteres a la condición              |
| `email`             | No            | Opcional            | Valida que el String a evaluar tenga formato de email                                              |
| `numericFormat`     | No            | Opcional            | Valida que el String a evaluar tenga un formato numérico                                           |
| `shouldOnlyContain` | Si (`String`) | Opcional            | Valida que el String a evaluar solo contenga caracteres incluidos en el String de condición        |
| `onlyNumbers`       | No            | Opcional            | Valida que el Staring a evaluar solo contenga caracteres numéricos                                 |
| `notContain`        | Si (`String`) | Opcional            | Valida que el String a evaluar no contenga algún carácter incluido en el String de la condición    |
| `mustContainOne`    | Si (`String`) | Opcional            | Valida que el String a evaluar contenga al menos un carácter incluido en el String de la condición |

Las reglas predefinidas pueden simplificar la definición de un Validator. 

```java
final Validator validator = new Validator.Builder()
    .required("Requerido")
    .minLength(6, "Se requieren más caracteres")
    .onlyNumbers("Solo números")
    .build();
```

El mensaje en las reglas predefinidas es opcional, ya que Validator ofrece mensaje predeterminados para cada una.

```java
final Validator validator = new Validator.Builder()
    .required()
    .minLength(6)
    .onlyNumbers()
    .build();
```

Los mensajes predeterminados se encuentran en las clases `MessagesEn` para los mensajes en inglés, y en `MessagesEs` 
para los mensajes en español, ambas clases implementan la interfaz `Messages`.

| Regla	              | Ingles                                                   | Español                                               |
|---------------------|----------------------------------------------------------|-------------------------------------------------------|
| `compare`	          | Not match                                                | No coinciden                                          |
| `required`	      | Required                                                 | Requerido                                             |
| `length`	          | It requires %d characters                                | Se requiere %d caracteres                             |
| `minLength`	      | It requires at least %d characters                       | Se requiere al menos %d caracteres                    |
| `maxLength`	      | It requires less than %d characters                      | Se requiere menos de %d caracteres                    |
| `email`             | Email invalid                                            | Email invalido                                        |
| `numericFormat`     | It is not a number                                       | No es un número                                       |
| `shouldOnlyContain` | They are just admitted the following characters %s       | Solo se admiten los siguientes caracteres %s          |
| `onlyNumbers`       | Just numbers                                             | Solo números                                          |
| `notContain`        | The following characters aren't admitted %s              | No se admiten los siguientes caracteres %s            |
| `mustContainOne`    | At least one of the following characters is required: %s | Se requiere al menos uno de los siguientes caracteres |

##### Nota:
- Por defecto se muestran los mensajes en Ingles

### Cambiar los mensajes por defecto
Validator posee un método estático llamado `.setMessages` el cual recibe como parámetro un objeto del tipo `Messages`.

```java
Validator.setMessages(new MessagesEs());
```

La interfaz `Messages` permite crear mensajes predeterminados personalizados.

```java
Validator.setMessages(new Messages() {

    @Override
    public String getNotMatchMessage() { return "Mensaje personalizado"; }

    @Override
    public String getRequireMessage() { return "Mensaje personalizado"; }

    @Override
    public String getLengthMessage() { return "Mensaje personalizado"; }

    @Override
    public String getMinLengthMessage() { return "Mensaje personalizado"; }

    @Override
    public String getMaxLengthMessage() { return "Mensaje personalizado"; }

    @Override
    public String getEmailMessage() { return "Mensaje personalizado"; }

    @Override
    public String getNumericFormat() { return "Mensaje personalizado"; }

    @Override
    public String getShouldOnlyContainMessage() { return "Mensaje personalizado"; }

    @Override
    public String getOnlyNumbersMessage() { return "Mensaje personalizado"; }

    @Override
    public String getNotContainMessage() { return "Mensaje personalizado"; }

    @Override
    public String getMustContainOneMessage() { return "Mensaje personalizado"; }

});
```

##### Nota:
- Los mensajes declarados junto una regla predefinida tienen prioridad sobre los mensajes predeterminados.
- Las reglas predefinidas que requieren un parámetro `condition`, hacen uso de `String.format` para formatear el 
mensaje con la condición.

### Recomendaciones

Comúnmente, suele haber varias instancias de Strings a cuáles aplicar las mismas reglas de validación. Para estos casos 
se recomienda definir los Validators por contexto, con el fin de definir nuestro Validator una vez y reutilizarlo. Esta 
lógica es posible, ya que Validator incluye el método `.copy` el cual genera copias del mismo.

```java
class Validators {

    public static final Validator email = new Validator.Builder()
        .required()
        .email()
        .build();

    public static final Validator password = new Validator.Builder()
        .required()
        .minLength(6)
        .build();

}

class Login {

    private final Validator emailValidator = Validators.email.copy();
    private final Validator pswValidator = Validators.password.copy();

    private String email, psw, pswConfirmation;

    public Form() {
        emailValidator.onNotPass(System.out::println);
        passwordValidator.onNotPass(System.out::println);
    }

    public void submit() {
        if (!emailValidator.isValid(email) || !pswValidator.compare(psw, pswConfirmation)) return;
        // ...
    }

}
```