# Validator

Facilitates the validation of Strings by chaining a series of rules.

## Translations
- [Spanish](translations/README-es.md)

## Release Notes 1.3.0
- Added annotations.
- Renamed the `dateFormat` rule to `date`.
- Renamed the `mustContainMinimum` rule to `mustContainMin`.
- Renamed the `isValidOrFail` function to `validOrFail`.

### Download the JAR
[Validator-1.3.0.jar](https://repo1.maven.org/maven2/io/github/ApamateSoft/Validator/1.3.0/Validator-1.3.0.jar)

### Maven
```xml
<dependency>
    <groupId>io.github.ApamateSoft</groupId>
    <artifactId>Validator</artifactId>
    <version>1.3.0</version>
</dependency>
```

### Gradle
```groovy
implementation group: 'io.github.ApamateSoft', name: 'Validator', version: '1.3.0'
```

## Starting

### My first validator

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {

    // Instantiating a new validator
    Validator validator = new Validator();

    // First rule, it will only be approved if the String to be evaluated is different from null, otherwise it will show
    // the message "Enter a text different from null"
    validator.rule("Enter a text other than null", (String evaluate) -> {
      return evaluate != null;
    });

    // Second rule, it will only be approved if the String to be evaluated is equal to "xxx", otherwise it will show the
    // message "The text is different from 'xxx'"
    validator.rule("The text is different from 'xxx'", (String evaluate) -> {
      return evaluate.equals("xxx");
    });
  }

}
```

The validator `rule` function allows you to create specific validation rules, associated with an error message in case
that this validation is not fulfilled.

##### *Note:*
- An indeterminate number of rules can be added.
- Rules will be evaluated in the order in which they were added.
- When a rule fails, the rest are ignored.
- A String is considered valid only if it passes all the rules.

### Simplifying code

You can create a Validator instance using `.Builder()`.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("Enter a text other than null", Objects::nonNull)
          .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
          .build();

}
```

### Predefined rules

Validator offers a series of predefined rules, trying to cover the most common validation cases.

| Rule	              | Description                                                                                                   |
|---------------------|---------------------------------------------------------------------------------------------------------------|
| `date`	          | Validates that the String to evaluate matches the specified date format                                       |
| `email`             | Validates that the String has an email format                                                                 |
| `expirationDate`    | Validates that the entered date has not expired                                                               |
| `httpLink`          | Validates that the String is a link with http format                                                          |
| `httpsLink`         | Validates that the String is a link with https format                                                         |
| `ip`                | Validates that the String is an ip format                                                                     |
| `ipv4`              | Validates that the String is an ipv4 format                                                                   |
| `ipv6`              | Validates that the String is an ipv6 format                                                                   |
| `length`	          | Validates that the String has an exact length of characters                                                   |
| `link`	          | Validates that the String is a link format                                                                    |
| `maxLength`	      | Validates that the length of the String is not greater than the condition                                     |
| `maxValue`	      | Validates that the value of the String is not greater than the condition                                      |
| `minAge`	          | Validates that the period from the entered date to the current date is greater than or equal to a minimum age |
| `minLength`	      | Validates that the length of the String is not less than the condition                                        |
| `minValue`	      | Validates that the value of the String is not less than the condition                                         |
| `mustContainMin`    | Validates that the String contains at least a minimum number of characters included in the condition          |
| `mustContainOne`    | Validates that the String contains at least one character included in the condition                           |
| `name`              | Validates that the String is a proper name                                                                    |
| `notContain`        | Validates that the String does not contain any characters included in the condition                           |
| `number`            | Validates that the String is a numeric format                                                                 |
| `numberPattern`     | Validates that the String matches the pattern, replacing the x's with numbers                                 |
| `onlyAlphanumeric`  | Validates that the String contains only alphanumeric characters                                               |
| `onlyLetters`       | Validates that the String contains only letters                                                               |
| `onlyNumbers`       | Validates that the String to evaluate only contains numeric characters                                        |
| `rangeLength`       | Validates that the length of the String is in the established range                                           |
| `rangeValue`        | Validates that the value of the String is in the established range                                            |
| `regExp`            | Validates that the String matches the regular expression                                                      |
| `required`	      | Validates that the String is different from null and empty                                                    |
| `shouldOnlyContain` | Validates that the String only contains characters included in the condition                                  |
| `time`              | Validates that the String is a time format                                                                    |
| `time12`            | Validates that the String is a time with 12-hour format                                                       |
| `time24`            | Validates that the String is a time with 24-hour format                                                       |
| `wwwLink`           | Validates that the String is a link with www format                                                           |

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

### Using annotations

Validator also offers the predefined rule set in the form of annotations.

| Annotations          | Description                                                                                                   |
|----------------------|---------------------------------------------------------------------------------------------------------------|
| `@Compare`           | Validates that the String to be evaluated matches the value of the attribute passed as parameter              |
| `@Date`              | Validates that the String to be evaluated matches the specified date format                                   |
| `@Email`             | Validates that the String has an email format                                                                 |
| `@ExpirationDate`    | Validates that the entered date has not expired                                                               |
| `@HttpLink`          | Validates that the String is a link in http format                                                            |
| `@HttpsLink`         | Validates that the String is a link with format https                                                         |
| `@ip`                | Validates that the String is a format of ip                                                                   |
| `@Ipv4`              | Validates that the String is an ipv4 format                                                                   |
| `@Ipv6`              | Validates that the String is an ipv6 format                                                                   |
| `@Length`            | Validates that the String has an exact length of characters                                                   |
| `@Link`              | Validates that the String is a binding format                                                                 |
| `@MaxLength`         | Validates that the length of the String is not greater than the condition                                     |
| `@MaxValue`          | Validates that the value of the String is not greater than the condition                                      |
| `@MinAge`            | Validates that the period from the entered date to the current date is greater than or equal to a minimum age |
| `@MinLength`         | Validates that the length of the String is not less than the condition                                        |
| `@MinValue`          | Validates that the value of the String is not less than the condition                                         |
| `@MustContainMin`    | Validates that the String contains at least a minimum number of characters included in the condition          |
| `@MustContainOne`    | Validates that the String contains at least one character included in the condition                           |
| `@Mummy`             | Validates that the String is a proper name                                                                    |
| `@MotContain`        | Validates that the String does not contain any characters included in the condition                           |
| `@Number`            | Validates that the String is a numeric format                                                                 |
| `@MumberPattern`     | Validates that the String matches the pattern, replacing the x's with numbers                                 |
| `@OnlyAlphanumeric`  | Validates that the String contains only alphanumeric characters                                               |
| `@OnlyLetters`       | Validates that the String contains only letters                                                               |
| `@OnlyNumbers`       | Validates that the String to evaluate only contains numeric characters                                        |
| `@RangeLength`       | Validates that the length of the String is in the established range                                           |
| `@RangeValue`        | Validates that the value of the String is in the established range                                            |
| `@RegExp`            | Validates that the String matches the regular expression                                                      |
| `@Required`          | Validates that the String is different from null and empty.                                                   |
| `@ShouldOnlyContain` | Validates that the String only contains characters included in the condition                                  |
| `@Time`              | Validates that the String is a time format                                                                    |
| `@Time12`            | Validates that the String is a time with 12-hour format                                                       |
| `@Time24`            | Validates that the String is a time with 24-hour format                                                       |
| `@WwwLink`           | Validates that the String is a link with format www                                                           |

Below is an example of how to implement annotations.

```java

import io.github.ApamateSoft.validator.annotations.Compare;
import io.github.ApamateSoft.validator.annotations.MinLength;

import static io.github.ApamateSoft.validator.utils.Alphabets.*;

public class HelloValidator {

    @Required(message = "Required")
    @MinLength(min = 8, message = "At least 8 characters required")
    @MustContainMin(min = 3, condition = ALPHA_LOWERCASE, message = "At least three lowercase letters are required")
    @MustContainMin(min = 3, condition = NUMBER, message = "At least 3 numbers are required")
    @MustContainOne(condition = ALPHA_UPPERCASE, message = "At least one capital letter is required")
    @MustContainOne(condition = "@#*-", message = "At least one special character is required")
    @Compare(compareWith = "passwordConfirm", message = "Do not match")
    private String password;
    private String passwordConfirm;

}
```

#### *Note:*
- Annotations simplify rule chaining, but do not allow adding custom rules.

### Default messages

The messages in the predefined rules and in the annotations are optional, so you can simplify the implementations 
as follows.

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

#### Annotation chaining:
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

The default messages are found in the `MessagesEn` classes for English messages, and in `MessagesEs`
for Spanish messages, both classes implement the `Messages` interface.

| Rule/Annotation     | English (Default)                                        | Spanish                                                   |
|---------------------|----------------------------------------------------------|-----------------------------------------------------------|
| `compare`           | Not match                                                | No coinciden                                              |
| `date`              | The date does not match the format %s                    | La fecha no coincide con el formato %s                    |
| `email`             | Email invalid                                            | Correo electrónico invalido                               |
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
| `name`              | Debe introducir un nombre personal válido                | Debe introducir un nombre personal válido                 |
| `notContain`        | The following characters aren't admitted %s              | No se admiten los siguientes caracteres %s                |
| `number`            | It is not a number                                       | No es un número                                           |
| `numberPattern`     | Does not match pattern %s                                | No coincide con el patrón %s                              |
| `onlyAlphanumeric`  | Just alphanumeric characters                             | Solo caracteres alfanuméricos                             |
| `onlyLetters`       | Only letters                                             | Solo letras                                               |
| `onlyNumbers`       | Just numbers                                             | Solo números                                              |
| `rangeLength`       | The text must contain between %d to %d characters        | El texto debe contener entre %d a %d caracteres           |
| `rangeValue`        | The value must be between %1$.2f and %1$.2f              | El valor debe estar entre %1$.2f y %1$.2f                 |
| `regExp`            | The value does not match the regular expression %s       | El valor no coincide con la expresión regular %s          |
| `required`          | Required                                                 | Requerido                                                 |
| `shouldOnlyContain` | They are just admitted the following characters %s       | Solo se admiten los siguientes caracteres %s              |
| `time`              | Time invalid                                             | Hora inválida                                             |
| `time12`            | Invalid 12 hour format                                   | Formato 12 horas inválido                                 |
| `time24`            | Invalid 24 hour format                                   | Formato 24 horas inválido                                 |
| `wwwLink`           | Invalid www link                                         | Enlace www inválido                                       |

#### Change default messages
Validator has a static method called `.setMessages` which receives an object of type `Messages` as a parameter.

#### Changing the message language

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

  public static void main(String[] args) {
    Validator.setMessages(new MessagesEs());
  }

}
```

### Validating a String

#### Working with events

The `.isValid` method is used to find out if the String is valid.

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

In case you want to compare two Strings, *which is very useful to validate passwords*, you can use the method`.compare`,
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

The `.onInvalidEvaluation` event is executed when a rule fails when it is evaluated and returns the error message
associated.

```java
import io.github.ApamateSoft.validator.Validator;

public class HelloValidator {

    private Validator validator = new Validator.Builder()
        .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
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

#### Work with exceptions

If you prefer not to use the `.onInvalidEvaluation` event, you can use the `.isValidOrFail` and
`.compareOrFail` overriding the `.isValid` and `.comapre` methods respectively.

The main difference is that these methods do not return any value and if they fail, they throw an exception to the type
`InvalidEvaluationException` containing the error message from the rule along with the value of the String to be 
evaluated.

```java
import io.github.ApamateSoft.validator.Validator;
import io.github.ApamateSoft.validator.exceptions.InvalidEvaluationException;

public class HelloValidator {

  Validator validator = new Validator.Builder()
          .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
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

#### Working with annotations

Validating a String using annotations is very similar to working with exceptions, but instead of using the
`.validOrFail` or `.compareOrFail` methods, the static `Validator.validOrFail` method must be used, and passed
as a parameter the class that contains the attributes to be evaluated.

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

## Utilities

### Alphabets

Some rules require as a parameter a set of characters that may or may not be required for validation. TO
that set is named as alphabet and validator returns a set of the alphabets as constants in the class
called `Alphabet`.

| Name               | Alphabet                                                                   |
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

### Regular expressions

Some rules, as in the case of `regExp`, can be passed a regular expression as a parameter. Validator too
offers a class with a set of regular expressions as constants in the `RegularExpressions` class.

| regular expression | match for                                                                                                        |
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

### Validators

All the rules use simple validators to validate if the String to be evaluated is valid or not. These validators are
static methods belonging to the `Validators` class that only return a boolean value.

| Validator	          | Description                                                                                                   |
|---------------------|---------------------------------------------------------------------------------------------------------------|
| `date`	          | Validates that the String to evaluate matches the specified date format                                       |
| `email`             | Validates that the String has an email format                                                                 |
| `expirationDate`    | Validates that the entered date has not expired                                                               |
| `httpLink`          | Validates that the String is a link with http format                                                          |
| `httpsLink`         | Validates that the String is a link with https format                                                         |
| `ip`                | Validates that the String is an ip format                                                                     |
| `ipv4`              | Validates that the String is an ipv4 format                                                                   |
| `ipv6`              | Validates that the String is an ipv6 format                                                                   |
| `length`	          | Validates that the String has an exact length of characters                                                   |
| `link`	          | Validates that the String is a link format                                                                    |
| `maxLength`	      | Validates that the length of the String is not greater than the condition                                     |
| `maxValue`	      | Validates that the value of the String is not greater than the condition                                      |
| `minAge`	          | Validates that the period from the entered date to the current date is greater than or equal to a minimum age |
| `minLength`	      | Validates that the length of the String is not less than the condition                                        |
| `minValue`	      | Validates that the value of the String is not less than the condition                                         |
| `mustContainMin`    | Validates that the String contains at least a minimum number of characters included in the condition          |
| `mustContainOne`    | Validates that the String contains at least one character included in the condition                           |
| `name`              | Validates that the String is a proper name                                                                    |
| `notContain`        | Validates that the String does not contain any characters included in the condition                           |
| `number`            | Validates that the String is a numeric format                                                                 |
| `numberPattern`     | Validates that the String matches the pattern, replacing the x's with numbers                                 |
| `onlyAlphanumeric`  | Validates that the String contains only alphanumeric characters                                               |
| `onlyLetters`       | Validates that the String contains only letters                                                               |
| `onlyNumbers`       | Validates that the String to evaluate only contains numeric characters                                        |
| `rangeLength`       | Validates that the length of the String is in the established range                                           |
| `rangeValue`        | Validates that the value of the String is in the established range                                            |
| `regExp`            | Validates that the String matches the regular expression                                                      |
| `required`	      | Validates that the String is different from null and empty                                                    |
| `shouldOnlyContain` | Validates that the String only contains characters included in the condition                                  |
| `time`              | Validates that the String is a time format                                                                    |
| `time12`            | Validates that the String is a time with 12-hour format                                                       |
| `time24`            | Validates that the String is a time with 24-hour format                                                       |
| `wwwLink`           | Validates that the String is a link with www format                                                           |

## Recommendations

Commonly, there are several instances of Strings to which to apply the same validation rules. for these cases
It is recommended to define Validators per context, in order to define our Validator once and reuse it. This
Logic is possible, since Validator includes a `.copy` method which generates copies of it.

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

    public Login() {
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

