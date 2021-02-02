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
| `length`	          | Si (`int`)    | Opcional            | Valida que el String a evaluar tenga la longitud exacta de carácteres a la condición               |
| `minLength`	      | Si (`int`)    | Opcional            | Valida que el String a evaluar tenga una longitud de caracteres minima a la condición              |
| `maxLength`	      | Si (`int`)    | Opcional            | Valida que el String a evaluar tenga una longitud maxima de carácteres a la condición              |
| `email`             | No            | Opcional            | Valida que el String a evaluar tenga formato de email                                              |
| `numericFormat`     | No            | Opcional            | Valida que el String a evaluar tenga un formato numérico                                           |
| `shouldOnlyContain` | Si (`String`) | Opcional            | Valida que el String a evaluar solo contenga carácteres incluidos en el String de condición        |
| `onlyNumbers`       | No            | Opcional            | Valida que el Staring a evaluar solo contenga carácteres numéricos                                 |
| `notContain`        | Si (`String`) | Opcional            | Valida que el String a evaluar no contenga algún carácter incluido en el String de la condición    |
| `mustContainOne`    | Si (`String`) | Opcional            | Valida que el String a evaluar contenga al menos un carácter incluido en el String de la condición |
