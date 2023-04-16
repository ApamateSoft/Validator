Versión en [Inglés](../README.md)

# Validator

Facilita la validación de Strings encadenando una serie de reglas.

## Notas de version 1.3.1
- Solucionado problema de traducción del mensaje por defecto de la regla name en inglés.
- Se ha cambiado el mensaje de error de la regla `onlyNumbers`.

## Instalación

### Descargar el JAR
[Validator-1.3.1.jar](https://repo1.maven.org/maven2/io/github/ApamateSoft/Validator/1.3.1/Validator-1.3.1.jar)

### Maven
```xml
<dependency>
    <groupId>io.github.ApamateSoft</groupId>
    <artifactId>Validator</artifactId>
    <version>1.3.1</version>
</dependency>
```

### Gradle
```groovy
implementation group: 'io.github.ApamateSoft', name: 'Validator', version: '1.3.1'
```

## Empezando

### Mi primer validator

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {

    // Instanciando un nuevo validator
    Validator validator = new Validator();

    // Primera regla, solo se aprobará si el String a evaluar es diferente de null, en caso contrario mostrara el
    // mensaje "Ingrese un texto diferente de null"
    validator.rule("Ingrese un texto diferente de null", (String evaluate) -> {
      return evaluate != null;
    });

    // Segunda regla, solo se aprobará si el String a evaluar es igual a "xxx", en caso contrario mostrará el mensaje
    // "El texto es diferente de 'xxx'"
    validator.rule("El texto es diferente de 'xxx'", (String evaluate) -> {
      return evaluate.equals("xxx");
    });
  }

}
```

La función `rule` de validator permite crear reglas de validación específicas, asociadas a un mensaje de error en caso
de que no se cumpla dicha validación.

##### *Nota:*
- Se puede agregar una cantidad indeterminada de reglas.
- Las reglas serán evaluadas en el orden en el cual fueron agregadas.
- Al momento de fallar una regla, se ignoran las restantes.
- Un String se considera válido, solo si este, pasa todas las reglas.

### Simplificando código

Puedes crear una instancia de Validator utilizando `.Builder()`.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("Ingrese un texto diferente de null", Objects::nonNull)
          .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
          .build();

}
```

### Reglas predefinidas

Validator ofrece una serie de reglas predefinidas, tratando de cubrir los casos mas comunes de validación.

| Regla	              | Descripción                                                                                              |
|---------------------|----------------------------------------------------------------------------------------------------------|
| `date`	          | Valida que el String a evaluar coincida con el formato de fecha especificado                             |
| `email`             | Valida que el String tenga un formato de correo electrónico                                              |
| `expirationDate`    | Valida que la fecha ingresada no haya expirado                                                           |
| `httpLink`          | Valida que el String sea un enlace con formato http                                                      |
| `httpsLink`         | Valida que el String sea un enlace con formato https                                                     |
| `ip`                | Valida que el String sea un formato de ip                                                                |
| `ipv4`              | Valida que el String sea un formato de ipv4                                                              |
| `ipv6`              | Valida que el String sea un formato de ipv6                                                              |
| `length`	          | Valida que el String tenga una longitud exacta de caracteres                                             |
| `link`	          | Valida que el String sea un formato de enlace                                                            |
| `maxLength`	      | Valida que la longitud del String no sea mayor que la condición                                          |
| `maxValue`	      | Valida que el valor del String no sea mayor que la condición                                             |
| `minAge`	          | Valida que el período desde la fecha ingresada hasta la fecha actual sea mayor o igual a una edad mínima |
| `minLength`	      | Valida que la longitud del String no sea menor que la condición                                          |
| `minValue`	      | Valida que el valor del String no sea menor que la condición                                             |
| `mustContainMin`    | Valida que el String contenga al menos un número mínimo de caracteres incluidos en la condición          |
| `mustContainOne`    | Valida que el String contenga al menos un caracter incluido en la condición                              |
| `name`              | Valida que el String sea un nombre propio                                                                |
| `notContain`        | Valida que el String no contenga ningún caracter incluido en la condición                                |
| `number`            | Valida que el String sea un formato numérico                                                             |
| `numberPattern`     | Valida que el String coincida con el patrón, reemplazando las x con números                              |
| `onlyAlphanumeric`  | Valida que el String contenga solo caracteres alfanuméricos                                              |
| `onlyLetters`       | Valida que el String contenga solo letras                                                                |
| `onlyNumbers`       | Valida que el String a evaluar solo contenga caracteres numéricos                                        |
| `rangeLength`       | Valida que la longitud del String esté en el rango establecido                                           |
| `rangeValue`        | Valida que el valor del String esté en el rango establecido                                              |
| `regExp`            | Valida que el String coincida con la expresión regular                                                   |
| `required`	      | Valida que el String sea diferente de nulo y vacío                                                       |
| `shouldOnlyContain` | Valida que el String solo contenga caracteres incluidos en la condición                                  |
| `time`              | Valida que el String sea un formato de hora                                                              |
| `time12`            | Valida que el String sea una hora con formato de 12 horas                                                |
| `time24`            | Valida que el String sea una hora con formato de 24 horas                                                |
| `wwwLink`           | Valida que el String sea un enlace con formato www                                                       |

Las reglas predefinidas pueden simplificar la definición de un Validator.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .required("Requerido")
          .minLength(6, "Se requieren más caracteres")
          .onlyNumbers("Solo números")
          .build();

}
```

### Utilizando anotaciones

Validator también ofrece el conjunto de reglas predefinidas en forma de anotaciones.

| Anotaciones          | Descripción                                                                                              |
|----------------------|----------------------------------------------------------------------------------------------------------|
| `@Compare`	       | Valida que el String a evaluar coincida con el valor del atributo pasado como parámetro                  |
| `@Date`	           | Valida que el String a evaluar coincida con el formato de fecha especificado                             |
| `@Email`             | Valida que el String tenga un formato de correo electrónico                                              |
| `@ExpirationDate`    | Valida que la fecha ingresada no haya expirado                                                           |
| `@HttpLink`          | Valida que el String sea un enlace con formato http                                                      |
| `@HttpsLink`         | Valida que el String sea un enlace con formato https                                                     |
| `@Ip`                | Valida que el String sea un formato de ip                                                                |
| `@Ipv4`              | Valida que el String sea un formato de ipv4                                                              |
| `@Ipv6`              | Valida que el String sea un formato de ipv6                                                              |
| `@Length`	           | Valida que el String tenga una longitud exacta de caracteres                                             |
| `@Link`	           | Valida que el String sea un formato de enlace                                                            |
| `@MaxLength`	       | Valida que la longitud del String no sea mayor que la condición                                          |
| `@MaxValue`	       | Valida que el valor del String no sea mayor que la condición                                             |
| `@MinAge`	           | Valida que el período desde la fecha ingresada hasta la fecha actual sea mayor o igual a una edad mínima |
| `@MinLength`	       | Valida que la longitud del String no sea menor que la condición                                          |
| `@MinValue`	       | Valida que el valor del String no sea menor que la condición                                             |
| `@MustContainMin`    | Valida que el String contenga al menos un número mínimo de caracteres incluidos en la condición          |
| `@MustContainOne`    | Valida que el String contenga al menos un caracter incluido en la condición                              |
| `@Mame`              | Valida que el String sea un nombre propio                                                                |
| `@MotContain`        | Valida que el String no contenga ningún caracter incluido en la condición                                |
| `@Number`            | Valida que el String sea un formato numérico                                                             |
| `@MumberPattern`     | Valida que el String coincida con el patrón, reemplazando las x con números                              |
| `@OnlyAlphanumeric`  | Valida que el String contenga solo caracteres alfanuméricos                                              |
| `@OnlyLetters`       | Valida que el String contenga solo letras                                                                |
| `@OnlyNumbers`       | Valida que el String a evaluar solo contenga caracteres numéricos                                        |
| `@RangeLength`       | Valida que la longitud del String esté en el rango establecido                                           |
| `@RangeValue`        | Valida que el valor del String esté en el rango establecido                                              |
| `@RegExp`            | Valida que el String coincida con la expresión regular                                                   |
| `@Required`	       | Valida que el String sea diferente de nulo y vacío.                                                      |
| `@ShouldOnlyContain` | Valida que el String solo contenga caracteres incluidos en la condición                                  |
| `@Time`              | Valida que el String sea un formato de hora                                                              |
| `@Time12`            | Valida que el String sea una hora con formato de 12 horas                                                |
| `@Time24`            | Valida que el String sea una hora con formato de 24 horas                                                |
| `@WwwLink`           | Valida que el String sea un enlace con formato www                                                       |

A continuación un ejemplo de como implementar las anotaciones.

```java

import io.github.ApamateSoft.validator.annotations.Compare;
import io.github.ApamateSoft.validator.annotations.MinLength;

import static io.github.ApamateSoft.validator.utils.Alphabets.*;

public class HelloValidator {

    @Required(message = "Requerido")
    @MinLength(min = 8, message = "Se requiere al menos 8 carácteres")
    @MustContainMin(min = 3, condition = ALPHA_LOWERCASE, message = "Se requiere al menos tres letras minúsculas")
    @MustContainMin(min = 3, condition = NUMBER, message = "Se require al menos 3 números")
    @MustContainOne(condition = ALPHA_UPPERCASE, message = "Se requiere al menos una mayúscula")
    @MustContainOne(condition = "@#*-", message = "Se requiere al menos un carácter especial")
    @Compare(compareWith = "passwordConfirm", message = "No coinciden")
    private String password;
    private String passwordConfirm;

}
```

#### *Nota:*
- Las anotaciones simplifican el encadenamiento de reglas, pero no permite agregar reglas personalizadas.

### Mensajes predeterminados

Los mensajes en las reglas predefinidas y en las anotaciones son opcionales, por lo cual se pueden simplificar las
implementaciones de las mismas de la siguiente manera.

#### Encadenamiento de reglas:
```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .required()
          .minLength(5)
          .onlyNumbers()
          .build();

}
```

#### Encadenamiento de anotaciones:
```java
import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.annotations.MinLength;
import io.github.ApamateSoft.validator.annotations.OnlyNumbers;
import io.github.ApamateSoft.validator.annotations.Required;

public class HelloValidator {

  @Required
  @MinLength(min = 5)
  @OnlyNumbers
  private String zip;

}
```

Los mensajes predeterminados se encuentran en las clases `MessagesEn` para los mensajes en inglés, y en `MessagesEs`
para los mensajes en español, ambas clases implementan la interfaz `Messages`.

| Regla/Anotación     | Inglés (Por defecto)                                     | Español                                                   |
|---------------------|----------------------------------------------------------|-----------------------------------------------------------|
| `compare`           | Not match                                                | No coinciden                                              |
| `date`              | The date does not match the format %s                    | La fecha no coincide con el formato %s                    |
| `email`             | Email invalid                                            | Correo electrónico inválido                               |
| `expirationDate`    | Expired date                                             | Fecha expirada                                            |
| `httpLink`          | Invalid http link                                        | Enlace http inválido                                      |
| `httpsLink`         | Invalid https link                                       | Enlace https inválido                                     |
| `ip`                | Invalid IP                                               | IP inválida                                               |
| `ipv4`              | Invalid IPv4                                             | IPv4 inválida                                             |
| `ipv6`              | Invalid IPv6                                             | IPv6 inválida                                             |
| `length`            | It requires %d characters                                | Se requiere %d caracteres                                 |
| `link`              | Invalid link                                             | Enlace inválido                                           |
| `maxLength`         | %d or less characters required                           | Se requiere %d o menos caracteres                         |
| `maxValue`          | The value cannot be greater than %1$.2f                  | El valor no puede ser mayor a %1$.2f                      |
| `minAge`            | You must be at least %d years old                        | Se debe tener al menos %d años                            |
| `minLength`         | %d or more characters are required                       | Se requiere %d o más caracteres                           |
| `minValue`          | The value cannot be less than %1$.2f                     | El valor no puede ser menor a %1$.2f                      |
| `mustContainMin`    | At least %d of the following characters are required: %s | Se requiere al menos %d de los siguientes caracteres: %s  |
| `mustContainOne`    | At least one of the following characters is required: %s | Se requiere al menos uno de los siguientes caracteres: %s |
| `name`              | Must enter a valid personal name                         | Debe introducir un nombre personal válido                 |
| `notContain`        | The following characters aren't admitted %s              | No se admiten los siguientes caracteres %s                |
| `number`            | It is not a number                                       | No es un número                                           |
| `numberPattern`     | Does not match pattern %s                                | No coincide con el patrón %s                              |
| `onlyAlphanumeric`  | Just alphanumeric characters                             | Solo caracteres alfanuméricos                             |
| `onlyLetters`       | Only letters                                             | Solo letras                                               |
| `onlyNumbers`       | Only numbers                                             | Solo números                                              |
| `rangeLength`       | The text must contain between %d to %d characters        | El texto debe contener entre %d a %d caracteres           |
| `rangeValue`        | The value must be between %1$.2f and %1$.2f              | El valor debe estar entre %1$.2f y %1$.2f                 |
| `regExp`            | The value does not match the regular expression %s       | El valor no coincide con la expresión regular %s          |
| `required`          | Required                                                 | Requerido                                                 |
| `shouldOnlyContain` | They are just admitted the following characters %s       | Solo se admiten los siguientes caracteres %s              |
| `time`              | Time invalid                                             | Hora inválida                                             |
| `time12`            | Invalid 12 hour format                                   | Formato 12 horas inválido                                 |
| `time24`            | Invalid 24 hour format                                   | Formato 24 horas inválido                                 |
| `wwwLink`           | Invalid www link                                         | Enlace www inválido                                       |

#### Cambiar los mensajes por defecto
Validator posee un método estático llamado `.setMessages` el cual recibe como parámetro un objeto del tipo `Messages`.

#### Cambiando el idioma de los mensajes

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {
    Validator.setMessages(new MessagesEs());
  }

}
```

### Validando un String

#### Trabajando con eventos

Se hace uso del método `.isValid` para saber si el String es válido.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {
    
  Validator validator = new Validator.Builder()
          .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
          .build();

  public static void main(String[] args) {
    validator.isValid("yyy"); // false
    validator.isValid("xxx"); // true
  }

}
```

En caso de querer comparar dos String, *lo cual es muy útil para validar contraseñas*, se puede hacer uso del método
`.compare`. Opcionalmente, se puede utilizar el método `.setNotMatchMessage` para definir el mensaje de error en caso de
no coincidir.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("Requerido", evaluate -> !evaluate.isEmpty())
          .setNotMatchMessage("No coinciden")
          .build();

  public static void main(String[] args) {
    validator.compare("abc", "xyz"); // false
    validator.compare("abc", "abc"); // true
  }

}
```

El evento `.onInvalidEvaluation` se ejecuta al fallar una regla cuando es evaluada y devuelve el mensaje de error 
asociado.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

    private Validator validator = new Validator.Builder()
        .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
        .build();
    
    public HelloValidator() {
        // Solo se ejecuta si falla la validación de alguna regla
        validator.onInvalidEvaluation(message -> System.out.println(message)); // "El texto es diferente de 'xxx'"
    }
  
    public void submit() {
        validator.isValid("yyy"); // false
        // TODO
    }

}
```

#### Trabajar con excepciones

Si se prefiere no utilizar el evento `.onInvalidEvaluation`, se puede usar los métodos `.isValidOrFail` y 
`.compareOrFail` en sustitución de los métodos `.isValid` y `.comapre` respectivamente.

La principal diferencia es que estos métodos no retorna valor alguno y en caso de fallar, arrojan una excepción del tipo
`InvalidEvaluationException` que contiene el mensaje de error de la regla junto con el valor del String evaluado.

```java
import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
          .build();

  private void submit() {
    try {
      validator.validOrFail("yyy");
      validator.compareOrFail("XXX", "YYY");
      // TODO
    } catch (InvalidEvaluationException e) {
      System.out.println(e.getMessage());
    }
  }

}
```

#### Trabajando con anotaciones

Validar un String utilizando anotaciones es muy similar, a trabajar con excepciones, pero en lugar de utilizar los 
métodos `.validOrFail` o `.compareOrFail`, se debe utilizar el método estático `Validator.validOrFail`, y se debe pasar
como parámetro la clase que contiene los atributos a evaluar.

```java
import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.annotations.NumberPattern;
import io.github.ApamateSoft.validator.annotations.Required;

class HelloValidator {

  @Required
  @NumberPattern(patter = "(xxxx) xxx xx xx")
  private String phone;

  private void submit() {
    try {
      Validator.validOrFail(this);
      // TODO
    } catch (InvalidEvaluationException e) {
      System.out.println(e.getMessage());
    }
  }
  
}
```

## Utilidades

### Alfabetos

Algunas reglas requieren como parámetro un conjunto de caracteres que pueden ser o no requeridos para la validación. A
dicho conjunto se le nombra como alfabeto y validator ofrece un conjunto de los alfabetos como constantes en la clase
llamada `Alphabets`.

| Nombre             | Alfabeto                                                                   |
|--------------------|----------------------------------------------------------------------------|
| BIN                | 01                                                                         |
| OCT                | 01234567                                                                   |
| HEX                | 0123456789aAbBcCdDeEfF                                                     |
| NUMBER             | 0123456789                                                                 |
| ALPHABET           | aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ                       |
| ALPHA_LOWERCASE    | abcdefghijklmnopqrstuvwxyz                                                 |
| ALPHA_UPPERCASE    | ABCDEFGHIJKLMNOPQRSTUVWXYZ                                                 |
| ALPHA_NUMERIC      | 0123456789aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ             |
| ALPHABET_ES        | aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚvVwWxXyYzZ           |
| ALPHA_LOWERCASE_ES | aábcdeéfghiíjklmnñoópqrstuúvwxyz                                           |
| ALPHA_UPPERCASE_ES | AÁBCDEÉFGHIÍJKLMNÑOÓPQRSTUÚVWXYZ                                           |
| ALPHA_NUMERIC_ES   | 0123456789aAáÁbBcCdDeEéÉfFgGhHiIíÍjJkKlLmMnNñÑoOóÓpPqQrRsStTuUúÚvVwWxXyYzZ |

### Expresiones regulares

Algunas reglas como en el caso de `regExp`, se le puede pasar como parámetro una expresión regular. Validator también
ofrece una clase con un conjunto de expresiones regulares como constantes en la clase `RegularExpressions`.

| Expresión regular  | Coincide para                                                                                                    |
|--------------------|------------------------------------------------------------------------------------------------------------------|
| EMAIL              | example@mail.com                                                                                                 |
| NUMBER             | 123456789                                                                                                        |
| ALPHA_NUMERIC      | abc123                                                                                                           |
| NAME               | jesus, maria, JOSE, jesus maria, Maria Jose, Jose Jesus, maria de jose                                           |
| DECIMAL            | 123, -123, "123.45, -123.45                                                                                      |
| LINK               | www.google.com, http://google.com, https://google.com, http://example.com/api/auth?name=Jesus                    |
| WWW_LINK           | www.google.com                                                                                                   |
| HTTP_LINK          | http://google.com                                                                                                |
| HTTPS_LINK         | https://google.com                                                                                               |
| IP                 | 127.0.0.1, 192.168.0.109, 10.0.0.1, ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff, ffff::, ffff::ffff, ffff:ffff::ffff |
| IPV4               | 127.0.0.1, 192.168.0.109, 10.0.0.1                                                                               |
| IPV6               | ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff, ffff::, ffff::ffff, ffff:ffff::ffff                                     |
| TIME               | 00:00, 12:30, 12:59 am, 23:59, 1:00 pm, 01:00AM, 01:00pm, 01:00PM                                                |
| TIME12             | 12:59 am, 1:00 pm, 01:00AM, 01:00pm                                                                              |
| TIME24             | 13:00, 23:59, 00:00                                                                                              |

### Validadores

Todas las reglas utilizan validadores simples para validar si el String a evaluar es válido o no. Estos validadores son
métodos estáticos pertenecientes a la clase `Validators` que solo retornan un valor booleano.

| Validador	          | Descripción                                                                                              |
|---------------------|----------------------------------------------------------------------------------------------------------|
| `date`	          | Valida que el String a evaluar coincida con el formato de fecha especificado                             |
| `email`             | Valida que el String tenga un formato de correo electrónico                                              |
| `expirationDate`    | Valida que la fecha ingresada no haya expirado                                                           |
| `httpLink`          | Valida que el String sea un enlace con formato http                                                      |
| `httpsLink`         | Valida que el String sea un enlace con formato https                                                     |
| `ip`                | Valida que el String sea un formato de ip                                                                |
| `ipv4`              | Valida que el String sea un formato de ipv4                                                              |
| `ipv6`              | Valida que el String sea un formato de ipv6                                                              |
| `length`	          | Valida que el String tenga una longitud exacta de caracteres                                             |
| `link`	          | Valida que el String sea un formato de enlace                                                            |
| `maxLength`	      | Valida que la longitud del String no sea mayor que la condición                                          |
| `maxValue`	      | Valida que el valor del String no sea mayor que la condición                                             |
| `minAge`	          | Valida que el período desde la fecha ingresada hasta la fecha actual sea mayor o igual a una edad mínima |
| `minLength`	      | Valida que la longitud del String no sea menor que la condición                                          |
| `minValue`	      | Valida que el valor del String no sea menor que la condición                                             |
| `mustContainMin`    | Valida que el String contenga al menos un número mínimo de caracteres incluidos en la condición          |
| `mustContainOne`    | Valida que el String contenga al menos un caracter incluido en la condición                              |
| `name`              | Valida que el String sea un nombre propio                                                                |
| `notContain`        | Valida que el String no contenga ningún caracter incluido en la condición                                |
| `number`            | Valida que el String sea un formato numérico                                                             |
| `numberPattern`     | Valida que el String coincida con el patrón, reemplazando las x con números                              |
| `onlyAlphanumeric`  | Valida que el String contenga solo caracteres alfanuméricos                                              |
| `onlyLetters`       | Valida que el String contenga solo letras                                                                |
| `onlyNumbers`       | Valida que el String a evaluar solo contenga caracteres numéricos                                        |
| `rangeLength`       | Valida que la longitud del String esté en el rango establecido                                           |
| `rangeValue`        | Valida que el valor del String esté en el rango establecido                                              |
| `regExp`            | Valida que el String coincida con la expresión regular                                                   |
| `required`	      | Valida que el String sea diferente de nulo y vacío.                                                      |
| `shouldOnlyContain` | Valida que el String solo contenga caracteres incluidos en la condición                                  |
| `time`              | Valida que el String sea un formato de hora                                                              |
| `time12`            | Valida que el String sea una hora con formato de 12 horas                                                |
| `time24`            | Valida que el String sea una hora con formato de 24 horas                                                |
| `wwwLink`           | Valida que el String sea un enlace con formato www                                                       |

## Recomendaciones

Comúnmente, suele haber varias instancias de Strings a cuáles aplicar las mismas reglas de validación. Para estos casos
se recomienda definir los Validators por contexto, con el fin de definir nuestro Validator una vez y reutilizarlo. Esta
lógica es posible, ya que Validator incluye el método `.copy` el cual genera copias del mismo.

```java
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.Validator;

import static io.github.ApamateSoft.validator.utils.Alphabets.*;

public class Validators {

    public static final Validator email = new Validator.Builder()
        .required()
        .email()
        .build();

    public static final Valiator phone = new Validator.Builder()
        .required()
        .numberPattern("(xxxx) xx-xx-xxx")
        .build();
    
    public static final Validator password = new Validator.Builder()
        .required()
        .minLength(12)
        .mustContainMin(3, ALPHA_LOWERCASE)
        .mustContainMin(3, ALPHA_UPPERCASE)
        .mustContainMin(3, NUMBER)
        .mustContainMin(3, "@~_/")
        .build();

}

public class Register {

    private final Validator emailValidator = Validators.email.copy();
    private final Validator pswValidator = Validators.password.copy();
    private final Validator phoneValidator = Validators.phone.copy();

    private String email, phone, psw, pswConfirmation;

    public Register() {
        emailValidator.onInvalidEvaluation(System.out::println);
        pswValidator.onInvalidEvaluation(System.out::println);
        phoneValidator.onInvalidEvaluation(System.out::println);
    }

    public void submit() {
        if (
            !emailValidator.isValid(email) || 
            !pswValidator.compare(psw, pswConfirmation) ||
            !phoneValidator.isValid(phone)
        ) return;
        // TODO
    }

    public void submitWithExceptions() {
        try {
            emailValidator.validOrFail(email);
            pswValidator.compareOrFail(psw, pswConfirmation);
            phoneValidator.validOrFail(phone);
            // TODO
        } catch (InvalidEvaluationException e) {
            System.out.println(e.getMessage());
        }
    }

}
```