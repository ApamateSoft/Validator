Versión en [Inglés](../README.md)

# Validator

Facilita la validación de Strings en java encadenando una serie de reglas.

## Notas de version

### Versión 1.2.0
Se ha renombrado la regla `numericFormat` a `number`.

Se han agregado las siguientes reglas:
- `dateFormat`
- `expirationDate`
- `httpLink`
- `httpsLink`
- `ip`
- `ipv4`
- `ipv6`
- `link`
- `maxValue`
- `minAge`
- `minValue`
- `mustContainMinimum`
- `mustContainOne`
- `name`
- `notContain`
- `numberPattern`
- `onlyAlphanumeric`
- `onlyCharacters`
- `rangeLength`
- `rangeValue`
- `regExp`
- `time`
- `time12`
- `time24`
- `wwwLink`

## Instalación

### Descargar el JAR
[Validator-1.2.0.jar](https://repo1.maven.org/maven2/io/github/ApamateSoft/Validator/1.2.0/Validator-1.2.0.jar)

### Maven
```xml
<dependency>
    <groupId>io.github.ApamateSoft</groupId>
    <artifactId>Validator</artifactId>
    <version>1.2.0</version>
</dependency>
```

### Gradle
```groovy
implementation group: 'io.github.ApamateSoft', name: 'Validator', version: '1.2.0'
```

## Empezando

```java
import com.apamatesoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {
      
    // Instanciando un nuevo validator
    Validator validator = new Validator();

    // Primera regla, solo se aprobará si el String a evaluar es diferente de null, en caso contrario mostrara el
    // mensaje "Ingrese un texto diferente de null"
    validator.rule("Ingrese un texto diferente de null", (String evaluate) -> {
      return evaluate!=null;
    } );

    // Segunda regla, solo se aprobará si el String a evaluar es igual a "xxx", en caso contrario mostrara el mensaje
    // "El texto es diferente de 'xxx'"
    validator.rule("El texto es diferente de 'xxx'", (String evaluate) -> {
      return evaluate.equals("xxx");
    } );
  }
  
}
```

##### Nota:
- Se puede agregar una cantidad indeterminada de reglas.
- Las reglas serán evaluadas en el orden en el cual fueron agregadas.
- Al momento de fallar una regla, se ignoran las restantes.
- Un String se considera válido, solo si este, pasa todas las reglas.

### Simplificando código

Puedes crear una instancia de Validator utilizando `.Builder()`. A continuación el ejemplo anterior simplificado.

```java
import com.apamatesoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("Ingrese un texto diferente de null", Objects::nonNull)
          .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
          .build();
    
}
```

### Validando un String

Se hace uso del método `.isValid` para saber si el String es válido.

```java
import com.apamatesoft.validator.Validator;

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
`.compare`, Opcionalmente se puede utilizar el método `.setNotMatchMessage` para definir el mensaje de error en caso de
no coincidir.

```java
import com.apamatesoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("Requerido", evaluate -> !evaluate.isEmpty() )
          .setNotMatchMessage("No coinciden")
          .build();
    
  public static void main(String[] args) {
    validator.compare("abc", "xyz"); // false
    validator.compare("abc", "abc"); // true
  }
    
}
```

## Capturando los mensajes de error

### Trabajando con eventos

El evento `.onNotPass` se ejecuta al fallar una regla cuando es evaluada y devuelve el mensaje asociado a la misma.

```java
import com.apamatesoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {
    Validator validator = new Validator.Builder()
            .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
            .build();

    // Solo se ejecuta si falla la validación de alguna regla
    validator.onNotPass( message -> System.out.println(message) ); // "El texto es diferente de 'xxx'"

    validator.isValid("yyy"); // false
  }
    
}
```
### Trabajar con excepciones

Si prefiere no utilizar el evento `.onNotPass`, puedes usar los métodos `.isValidOrFail` y `.compareOrFail` en
sustitución de los métodos `.isValid` y `.comapre` respectivamente.

La principal diferencia es que estos métodos no retorna valor alguno y en caso de fallar, en cambio, arrojan una 
excepción del tipo `InvalidEvaluationException` que contiene el mensaje de error de la regla junto con el valor del
String a evaluar.

```java
import com.apamatesoft.validator.Validator;
import com.apamatesoft.validator.exceptions.InvalidEvaluationException;

class HelloValidator {

    Validator validator = new Validator.Builder()
            .rule("El texto es diferente de 'xxx'", evaluate -> evaluate.equals("xxx"))
            .build();

    private void submit() {
        try {
            validator.isValidOrFail("yyy");
            validator.compareOrFail("XXX", "YYY");
        } catch (InvalidEvaluationException e) {
            System.out.println(e.getMessage());
        }
    }

}
```

## Reglas predefinidas

Validator ofrece una serie de reglas predefinidas.

| Regla	               | Descripción                                                                                              |
|----------------------|----------------------------------------------------------------------------------------------------------|
| `dateFormat`	       | Valida que el String a evaluar coincida con el formato de fecha especificado                             |
| `email`              | Valida que el String tenga un formato de correo electrónico                                              |
| `expirationDate`     | Valida que la fecha ingresada no haya expirado                                                           |
| `httpLink`           | Valida que el String sea un enlace con formato http                                                      |
| `httpsLink`          | Valida que el String sea un enlace con formato https                                                     |
| `ip`                 | Valida que el String sea un formato de ip                                                                |
| `ipv4`               | Valida que el String sea un formato de ipv4                                                              |
| `ipv6`               | Valida que el String sea un formato de ipv6                                                              |
| `length`	           | Valida que el String tenga una longitud exacta de caracteres                                             |
| `link`	           | Valida que el String sea un formato de enlace                                                            |
| `maxLength`	       | Valida que la longitud del String no sea mayor que la condición                                          |
| `maxValue`	       | Valida que el valor del String no sea mayor que la condición                                             |
| `minAge`	           | Valida que el período desde la fecha ingresada hasta la fecha actual sea mayor o igual a una edad mínima |
| `minLength`	       | Valida que la longitud del String no sea menor que la condición                                          |
| `minValue`	       | Valida que el valor del String no sea menor que la condición                                             |
| `mustContainMinimum` | Valida que una expresión regular se repita una cantidad mínima                                           |
| `mustContainOne`     | Valida que el String contenga al menos un carácter incluido en la condición                              |
| `name`               | Valida que el String sea un nombre propio                                                                |
| `notContain`         | Valida que el String no contenga ningún carácter incluido en la condición                                |
| `number`             | Valida que el String sea un formato númerico                                                             |
| `numberPattern`      | Valida que el String coincida con el patrón, reemplazando las x con números                              |
| `onlyAlphanumeric`   | Valida que el String contenga solo caracteres alfanuméricos                                              |
| `onlyCharacters`     | Valida que el String contenga solo caracteres alfabéticos                                                |
| `onlyNumbers`        | Valida que el String a evaluar solo contenga caracteres numéricos                                        |
| `rangeLength`        | Valida que la longitud del String esté en el rango establecido                                           |
| `rangeValue`         | Valida que el valor del String esté en el rango establecido                                              |
| `regExp`             | Valida que el String coincida con la expresión regular                                                   |
| `required`	       | Valida que el String sea diferente de nulo y vacío.                                                      |
| `shouldOnlyContain`  | Valida que el String solo contenga caracteres incluidos en la condición                                  |
| `time`               | Valida que el String sea un formato de hora                                                              |
| `time12`             | Valida que el String sea una hora con formato de 12 horas                                                |
| `time24`             | Valida que el String sea una hora con formato de 24 horas                                                |
| `wwwLink`            | Valida que el String sea un enlace con formato www                                                       |

Las reglas predefinidas pueden simplificar la definición de un Validator.

```java
import com.apamatesoft.validator.Validator;

public class HelloValidator {
      
    Validator validator = new Validator.Builder()
            .required("Requerido")
            .minLength(6, "Se requieren más caracteres")
            .onlyNumbers("Solo números")
            .build();
    
}
```

El mensaje en las reglas predefinidas es opcional, ya que Validator ofrece mensajes predeterminados para cada una.

```java
import com.apamatesoft.validator.Validator;

public class HelloValidator {

   Validator validator = new Validator.Builder()
          .required()
          .minLength(6)
          .onlyNumbers()
          .build();
    
}
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
- Por defecto se muestran los mensajes en Inglés.

## Cambiar los mensajes por defecto
Validator posee un método estático llamado `.setMessages` el cual recibe como parámetro un objeto del tipo `Messages`.

### Cambiando el idioma de los mensajes a español.

```java
import com.apamatesoft.validator.Validator;

public class HelloValidator {
    
  public static void main(String[] args) {
    Validator.setMessages(new MessagesEs());
  }
  
}
```

### Mensajes personalizados.

La interfaz `Messages` permite crear mensajes predeterminados personalizados.

```java
public class HelloValidator {

  public static void main(String[] args) {
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
  }
    
}
```

##### Nota:
- Los mensajes declarados junto a una regla predefinida tienen prioridad sobre los mensajes predeterminados.
- Las reglas predefinidas que requieren un parámetro `condition`, hacen uso de `String.format` para formatear el
  mensaje con la condición.

### Recomendaciones

Comúnmente, suele haber varias instancias de Strings a cuáles aplicar las mismas reglas de validación. Para estos casos
se recomienda definir los Validators por contexto, con el fin de definir nuestro Validator una vez y reutilizarlo. Esta
lógica es posible, ya que Validator incluye el método `.copy` el cual genera copias del mismo.

```java
public class Validators {

  public static final Validator email = new Validator.Builder()
          .required()
          .email()
          .build();

  public static final Validator password = new Validator.Builder()
          .required()
          .minLength(6)
          .build();

}

public class Login {

  private final Validator emailValidator = Validators.email.copy();
  private final Validator pswValidator = Validators.password.copy();

  private String email, psw, pswConfirmation;

  public Login() {
    emailValidator.onNotPass(System.out::println);
    passwordValidator.onNotPass(System.out::println);
  }

  // Utilizando eventos.
  public void submit() {
    if (!emailValidator.isValid(email) || !pswValidator.compare(psw, pswConfirmation)) return;
    // TODO
  }

  // Utilizando excepciones
  public void submit() {
    try {
      emailValidator.isValidOrFail(email);
      pswValidator.compareOrFail(psw, pswConfirmation);
      // TODO
    } catch (InvalidEvaluationException e) {
      System.out.println(e.getMessage());
    }
  }

}
```
