# Validator
## Valida Strings de forma fácil 
Validador es una librería escrita en Java, que pretende simplificar la validación de Strings, 
Implementando una serie de reglas que este debe de seguir.
### Instalación
Instrucciones de instalación pendiente.

### Empezando
ejemplo de como crear un validator con dos reglas.

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

##### Algunas observaciones:
-	Se puede agregar n cantidad de reglas.
-	Validator aplicara una a una las reglas en el contenidas, en el orden en que fueron agregadas.
-	Al momento de romperse la primera regla, dejara de evaluarse las demás en la cola. 
-	Solo se considera el String aprobado si este pasa todas las pruebas.

##### Simplificando el código anterior
Validator implementa el patrón de diseño builder, con el fin de agilizar la instanciación del mismo. 
```java
Validator validator = new Validator.Builder()
    .rule("Ingrese un texto diferente de null", Objects::nonNull)
    .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();
```

#### Aplicando el Validator a un String

Se hace uso del método `.isValid` de Validator para aplicar sobre un String las reglas en el contenidas.

###### por ejemplo:
```java
Validator validator = new Validator.Builder()
    .rule("Ingrese un texto diferente de null", Objects::nonNull)
    .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();

validator.isValid("yyy"); // false, con el mensaje: "El texto es diferente de 'xxx'"
validator.isValid("xxx"); // true
```

En caso de querer comparar dos String, lo cual es muy útil para validar contraseñas, se puede hacer uso del método. 
`.compare`, el cual compara dos String antes de aplicar las reglas. Opcionalmente se puede utilizar el método 
`.setNotMatchMessage` para definir el mensaje de error a mostrar en caso de que los String no coincidan.  

###### por ejemplo: 

```java
final Validator validator = new Validator.Builder()
    .rule("Requerido", evaluate -> !evaluate.isEmpty() )
    .setNotMatchMessage("No coinciden")
    .build();

validator.compare("abc", "xyz"); // false
validator.compare("abc", "abc"); // true
```

#### Capturando los mensajes de error
Validator posee un callback que se ejecuta al momento de no cumplirse una regla y devuelve el mensaje de la misma. Para 
subscribirse al callback se hace uso del método `.onNotPass` de Validator.

###### por ejemplo:
```java
Validator validator = new Validator.Builder()
    .rule("Ingrese un texto diferente de null", Objects::nonNull)
    .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();

// Solo se ejecuta si falla la validación de alguna regla
validator.onNotPass( message -> System.out.println(message) ); // "El texto es diferente de 'xxx'"

validator.isValid("yyy"); // false
```

### Reglas predefinidas

Validator agrega en su biblioteca una serie de reglas predefinidas.

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

###### por ejemplo
```java
final Validator validator = new Validator.Builder()
    .required("Requerido")
    .minLength(6, "Se requieren más caracteres")
    .onlyNumbers("Solo números")
    .build();
```

El mensaje de error en las reglas predefinidas es opcional ya que Validator implementa mensaje predefinidos de error 
para cada uno.

###### por ejemplo
```java
final Validator validator = new Validator.Builder()
    .required()
    .minLength(6)
    .onlyNumbers()
    .build();
```

Los mensajes se encuentran en las clases `MessagesEn` para los mensajes en inglés, y en `MessagesEs` para los mensajes 
en español, ambas implementan la interfaz `Messages`.

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

Por defecto se muestran los mensajes en Ingles

### Cambiar los mensajes por defecto
Validator posee un método estático llamado `.setMessages` el cual recibe como parámetro un objeto del tipo `Messages`.

###### por ejemplo
```java
Validator.setMessages(new MessagesEs());
```

La interfaz `Messages` da libertad para modificar los mensajes predefinidos

###### por ejemplo
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

##### NOTA:
- Los mensajes declarados como atributos de una regla predefinida tienen prioridad sobre los mensajes predefinidos.
- Las reglas predefinidas que requieren una parámetro `condition`, hacen uso de `String.format` para formatear el 
mensaje de error con la condición.

### Recomendaciones

Comúnmente, suele haber varias instancias de Strings a cuáles se le debe aplicar las mismas reglas de validación, ya sea 
que estén en el mismo archivo o no.

Para estos casos se recomienda definir los Validators por contexto de uso y englobarlo en un mismo documento.

Esto es posible ya que Validator incluye el método `.copy` el cual permite cuantas copias del mismo sean necesarias.

###### por ejemplo
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
    private final Validator passwordValidator = Validators.password.copy();

    private String email, password, passwordConfirmation;

    public Form() {
        emailValidator.onNotPass(System.out::println);
        passwordValidator.onNotPass(System.out::println);
    }

    public void submit() {
        if (!emailValidator.isValid(email) || !passwordValidator.compare(password, passwordConfirmation)) return;
        // TODO: Paso la validación
    }

}
```

