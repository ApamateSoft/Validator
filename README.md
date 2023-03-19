# Validator

Facilitates the validation of Strings by chaining a series of rules.

## Translations
- [Spanish](translations/README-es.md)

## Release Notes 1.2.0

- The `onNotPass` event has been renamed to `onInvalidEvaluation`.
- Renamed the package id from `com.apamatesoft.validator` to `io.github.ApamateSoft.validator`.
- Renamed the `numericFormat` rule to `number`.
- The following predefined rules have been added:
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
  - `onlyLetters`
  - `rangeLength`
  - `rangeValue`
  - `regExp`
  - `time`
  - `time12`
  - `time24`
  - `wwwLink`

## Installation

### Download the JAR
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

## Getting started

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {

    // Instantiating a new validator
    Validator validator = new Validator();

    // First rule, it will only be approved if the String to be evaluated is different from null, otherwise it will show 
    // the message "Enter a text different from null"
    validator.rule("Ingrese un texto diferente de null", (String evaluate) -> {
      return evaluate != null;
    });

    // Second rule, it will only be approved if the String to be evaluated is equal to "xxx", otherwise it will show the 
    // message "The text is different from 'xxx'"
    validator.rule("El texto es diferente de 'xxx'", (String evaluate) -> {
      return evaluate.equals("xxx");
    });
  }

}
```

##### Note:
- Any number of rules can be added.
- Rules will be evaluated in the order in which they were added.
- When a rule fails, the rest are ignored.
- A String is considered valid, only if it passes all the rules.

### Simplifying code

You can create a Validator instance using `.Builder()`. Below is the simplified example above.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("Enter a text other than null", Objects::nonNull)
          .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
          .build();

}
```

### Validating a String

The `.isValid` method is used to know if the String is valid.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
          .build();

  public static void main(String[] args) {
    validator.isValid("yyy"); // false
    validator.isValid("xxx"); // true
  }

}
```

In case you want to compare two Strings, which is very useful to validate passwords, you can use the method `.compare`, 
Optionally you can use the `.setNotMatchMessage` method to define the error message in case of not match.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("Required", evaluate -> !evaluate.isEmpty())
          .setNotMatchMessage("Not match")
          .build();

  public static void main(String[] args) {
    validator.compare("abc", "xyz"); // false
    validator.compare("abc", "abc"); // true
  }

}
```

## Capturing the error messages

### Working with events

The `.onInvalidEvaluation` event is executed when a rule fails when it is evaluated and returns the message associated 
with it.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {
    Validator validator = new Validator.Builder()
            .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
            .build();

    // It is only executed if the validation of some rule fails
    validator.onInvalidEvaluation(message -> System.out.println(message)); // "The text is different from 'xxx'"

    validator.isValid("yyy"); // false
  }

}
```

### work with exceptions

If you prefer not to use the `.onInvalidEvaluation` event, you can use the `.isValidOrFail` and `.compareOrFail` methods
on substitution of the `.isValid` and `.comapre` methods respectively.

The main difference is that these methods do not return any value and in case of failure, instead, they return an 
exception to type `InvalidEvaluationException` containing the error message of the rule along with the value of the
String to evaluate.

```java
import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;

class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
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

### Predefined rules

Validator offers a series of predefined rules.

| Rule	               | Description                                                                                                   |
|----------------------|---------------------------------------------------------------------------------------------------------------|
| `dateFormat`	       | Validates that the String to evaluate matches the specified date format                                       |
| `email`              | Validates that the String has an email format                                                                 |
| `expirationDate`     | Validates that the entered date has not expired                                                               |
| `httpLink`           | Validates that the String is a link with http format                                                          |
| `httpsLink`          | Validates that the String is a link with https format                                                         |
| `ip`                 | Validates that the String is an ip format                                                                     |
| `ipv4`               | Validates that the String is an ipv4 format                                                                   |
| `ipv6`               | Validates that the String is an ipv6 format                                                                   |
| `length`	           | Validates that the String has an exact length of characters                                                   |
| `link`	           | Validates that the String is a link format                                                                    |
| `maxLength`	       | Validates that the length of the String is not greater than the condition                                     |
| `maxValue`	       | Validates that the value of the String is not greater than the condition                                      |
| `minAge`	           | Validates that the period from the entered date to the current date is greater than or equal to a minimum age |
| `minLength`	       | Validates that the length of the String is not less than the condition                                        |
| `minValue`	       | Validates that the value of the String is not less than the condition                                         |
| `mustContainMinimum` | Validates that the String contains at least a minimum number of characters included in the condition          |
| `mustContainOne`     | Validates that the String contains at least one character included in the condition                           |
| `name`               | Validates that the String is a proper name                                                                    |
| `notContain`         | Validates that the String does not contain any characters included in the condition                           |
| `number`             | Validates that the String is a numeric format                                                                 |
| `numberPattern`      | Validates that the String matches the pattern, replacing the x's with numbers                                 |
| `onlyAlphanumeric`   | Validates that the String contains only alphanumeric characters                                               |
| `onlyLetters`        | Validates that the String contains only letters                                                               |
| `onlyNumbers`        | Validates that the String to evaluate only contains numeric characters                                        |
| `rangeLength`        | Validates that the length of the String is in the established range                                           |
| `rangeValue`         | Validates that the value of the String is in the established range                                            |
| `regExp`             | Validates that the String matches the regular expression                                                      |
| `required`	       | Validates that the String is different from null and empty                                                    |
| `shouldOnlyContain`  | Validates that the String only contains characters included in the condition                                  |
| `time`               | Validates that the String is a time format                                                                    |
| `time12`             | Validates that the String is a time with 12-hour format                                                       |
| `time24`             | Validates that the String is a time with 24-hour format                                                       |
| `wwwLink`            | Validates that the String is a link with www format                                                           |

Predefined rules can simplify the definition of a Validator.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .required("Required")
          .minLength(6, "More characters are required")
          .onlyNumbers("Only numbers")
          .build();

}
```

The message in the predefined rules is optional, as Validator offers default messages for each one. By Therefore, the 
above code could be simplified as follows.
```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .required()
          .minLength(6)
          .onlyNumbers()
          .build();

}
```

The default messages are found in the `MessagesEn` classes for English messages, and in `MessagesEs` for Spanish 
messages, both classes implement the `Messages` interface.

| Rule	               | English                                                  | Spanish                                                   |
|----------------------|----------------------------------------------------------|-----------------------------------------------------------|
| `dateFormat`         | The date does not match the format %s                    | La fecha no coincide con el formato %s                    |
| `email`              | Email invalid                                            | Correo electrónico invalido                               |
| `expirationDate`     | Expired date                                             | Fecha expirada                                            |
| `httpLink`           | Invalid http link                                        | Enlace http inválido                                      |
| `httpsLink`          | Invalid https link                                       | Enlace https inválido                                     |
| `ip`                 | Invalid IP                                               | IP inválida                                               |
| `ipv4`               | Invalid IPv4                                             | IPv4 inválida                                             |
| `ipv6`               | Invalid IPv6                                             | IPv6 inválida                                             |
| `length`             | It requires %d characters                                | Se requiere %d caracteres                                 |
| `link`               | Invalid link                                             | Enlace inválido                                           |
| `maxLength`          | %d or less characters required                           | Se requiere %d o menos caracteres                         |
| `maxValue`           | The value cannot be greater than %1$.2f                  | El valor no puede ser mayor a %1$.2f                      |
| `minAge`             | You must be at least %d years old                        | Se debe tener al menos %d años                            |
| `minLength`          | %d or more characters are required                       | Se requiere %d o más caracteres                           |
| `minValue`           | The value cannot be less than %1$.2f                     | El valor no puede ser menor a %1$.2f                      |
| `mustContainMinimum` | At least %d of the following characters are required: %s | Se requiere al menos %d de los siguientes caracteres: %s  |
| `mustContainOne`     | At least one of the following characters is required: %s | Se requiere al menos uno de los siguientes caracteres: %s |
| `name`               | Debe introducir un nombre personal válido                | Debe introducir un nombre personal válido                 |
| `notContain`         | The following characters aren't admitted %s              | No se admiten los siguientes caracteres %s                |
| `notMatch`           | Not match                                                | No coinciden                                              |
| `number`             | It is not a number                                       | No es un número                                           |
| `numberPattern`      | Does not match pattern %s                                | No coincide con el patrón %s                              |
| `onlyAlphanumeric`   | Just alphanumeric characters                             | Solo caracteres alfanuméricos                             |
| `onlyLetters`        | Only letters                                             | Solo letras                                               |
| `onlyNumbers`        | Just numbers                                             | Solo números                                              |
| `rangeLength`        | The text must contain between %d to %d characters        | El texto debe contener entre %d a %d caracteres           |
| `rangeValue`         | The value must be between %1$.2f and %1$.2f              | El valor debe estar entre %1$.2f y %1$.2f                 |
| `regExp`             | The value does not match the regular expression %s       | El valor no coincide con la expresión regular %s          |
| `required`           | Required                                                 | Requerido                                                 |
| `shouldOnlyContain`  | They are just admitted the following characters %s       | Solo se admiten los siguientes caracteres %s              |
| `time`               | Time invalid                                             | Hora inválida                                             |
| `time12`             | Invalid 12 hour format                                   | Formato 12 horas inválido                                 |
| `time24`             | Invalid 24 hour format                                   | Formato 24 horas inválido                                 |
| `wwwLink`            | Invalid www link                                         | Enlace www inválido                                       |

##### Note:
- By default messages are shown in English

## Change default messages
Validator has a static method called `.setMessages` which receives an object of type `Messages` as a parameter.

### Changing the language of the messages.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {
    Validator.setMessages(new MessagesEs());
  }

}
```

### Personalized messages.

The `Messages` interface allows you to create custom default messages.

```java
import io.github.ApamateSoft.validator.messages.Messages;

public class HelloValidator {

  public static void main(String[] args) {
    Validator.setMessages(new Messages() {

      @Override
      public String getDateFormatMessage() { return "personalized message"; }

      @Override
      public String getEmailMessage() { return "personalized message"; }

      @Override
      public String getExpirationDateMessage() { return "personalized message"; }

      @Override
      public String getHttpLinkMessage() { return "personalized message"; }

      @Override
      public String getHttpsLinkMessage() { return "personalized message"; }

      @Override
      public String getIpMessage() { return "personalized message"; }

      @Override
      public String getIpv4Message() { return "personalized message"; }

      @Override
      public String getIpv6Message() { return "personalized message"; }

      @Override
      public String getLengthMessage() { return "personalized message"; }

      @Override
      public String getLinkMessage() { return "personalized message"; }

      @Override
      public String getMaxLengthMessage() { return "personalized message"; }

      @Override
      public String getMaxValueMessage() { return "personalized message"; }

      @Override
      public String getMinAgeMessage() { return "personalized message"; }

      @Override
      public String getMinLengthMessage() { return "personalized message"; }

      @Override
      public String getMinValueMessage() { return "personalized message"; }

      @Override
      public String getMustContainMinimumMessage() { return "personalized message"; }

      @Override
      public String getMustContainOneMessage() { return "personalized message"; }

      @Override
      public String getNameMessage() { return "personalized message"; }

      @Override
      public String getNotContainMessage() { return "personalized message"; }

      @Override
      public String getNotMatchMessage() { return "personalized message"; }

      @Override
      public String getNumberMessage() { return "personalized message"; }

      @Override
      public String getNumberPatternMessage() { return "personalized message"; }

      @Override
      public String getOnlyAlphanumericMessage() { return "personalized message"; }

      @Override
      public String getOnlyCharactersMessage() { return "personalized message"; }

      @Override
      public String getOnlyNumbersMessage() { return "personalized message"; }

      @Override
      public String getRangeLengthMessage() { return "personalized message"; }

      @Override
      public String getRangeValueMessage() { return "personalized message"; }

      @Override
      public String getRegExpMessage() { return "personalized message"; }

      @Override
      public String getRequireMessage() { return "personalized message"; }

      @Override
      public String getShouldOnlyContainMessage() { return "personalized message"; }

      @Override
      public String getTimeMessage() { return "personalized message"; }

      @Override
      public String getTime12Message() { return "personalized message"; }

      @Override
      public String getTime24Message() { return "personalized message"; }

      @Override
      public String getWwwLinkMessage() { return "personalized message"; }
    });
  }

}

```

##### Note:
- Messages declared next to a predefined rule take precedence over default messages.
- Predefined rules that require a `condition` parameter, make use of` String.format` to format the message with the 
  condition.

### Recommendations

Commonly, there are multiple instances of Strings to which to apply the same validation rules. For these cases It is 
recommended to define the Validators by context, in order to define our Validator once and reuse it. Is Logic is 
possible, since Validator includes the `.copy` method which generates copies of it.

```java
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;
import io.github.ApamateSoft.validator.Validator;

import static io.github.ApamateSoft.validator.utils.Constants.*;

public class Validators {

  public static final Validator email = new Validator.Builder()
          .required()
          .email()
          .build();

  public static final Validator password = new Validator.Builder()
          .required()
          .minLength(9)
          .mustContainMinimum(3, ALPHA_LOWERCASE)
          .mustContainMinimum(3, ALPHA_UPPERCASE)
          .mustContainMinimum(3, NUMBER)
          .mustContainMinimum(3, "@~_/")
          .build();

}

public class Login {

  private final Validator emailValidator = Validators.email.copy();
  private final Validator pswValidator = Validators.password.copy();

  private String email, psw, pswConfirmation;

  public Login() {
    emailValidator.onInvalidEvaluation(System.out::println);
    pswValidator.onInvalidEvaluation(System.out::println);
  }

  public void submit() {
    if (!emailValidator.isValid(email) || !pswValidator.compare(psw, pswConfirmation)) return;
    // TODO
  }

  public void submitWithExceptions() {
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
