# Validator

Validator is a library written in Java, which aims to simplify the validation of Strings by declaring a series of rules.

## Translations
- [Español](translations/README-es.md)

## Release notes

### Version 1.1.0

- `.isValidOrFail` method has been added.
- `.compareOrFail` method has been added.

## Installation

### Download the JAR
[Validator-1.1.0.jar](https://repo1.maven.org/maven2/io/github/ApamateSoft/Validator/1.1.0/Validator-1.1.0.jar)

### Maven
```xml
<dependency>
    <groupId>io.github.ApamateSoft</groupId>
    <artifactId>Validator</artifactId>
    <version>1.1.0</version>
</dependency>
```

### Gradle
```groovy
implementation group: 'io.github.ApamateSoft', name: 'Validator', version: '1.1.0'
```

## Getting started

```java
import com.apamatesoft.validator.Validator;

public class Example {

  // Instantiating a new validator
  private Validator validator = new Validator();
  
    public Example() {
      // 1º rule: it will only be approved if the String to evaluate is different from null, otherwise it will show the 
      // message "Enter a text other than null"
      validator.rule("Enter a text other than null", (String evaluate) -> {
        return evaluate!=null;
      } );
      
      // 2º rule: it will only be approved if the String to evaluate is equal to "xxx", otherwise it will show the message 
      // "The text is different from 'xxx'"
      validator.rule("The text is different from 'xxx'", (String evaluate) -> {
        return evaluate.equals("xxx");
      } );
    }
    
}
```

##### Note:
- You can add n number of rules.
- Validator will apply the rules one by one, in the order in which they were added.
- When a rule fails, the rest are ignored.
- A String is considered valid, only if it passes all the rules.

### Simplifying code

You can create a Validator instance using `.Builder()`.

```java
import com.apamatesoft.validator.Validator;

public class Example {

  // Instantiating a new validator with builder
  private Validator validator = new Validator.Builder()
    .rule("Enter a text other than null", Objects::nonNull)
    .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();
  
}

```

### Validating a String

The `.isValid` method is used to know if the String is valid.

```java
Validator validator = new Validator.Builder()
    .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();

validator.isValid("yyy"); // false
validator.isValid("xxx"); // true
```

In case you want to compare two Strings, which is very useful to validate passwords, you can use the method.
`.compare`. Optionally you can use the `.setNotMatchMessage` method to define the error message in case of
not match.

```java
final Validator validator = new Validator.Builder()
    .rule("Required", evaluate -> !evaluate.isEmpty() )
    .setNotMatchMessage("Do not match")
    .build();

validator.compare("abc", "xyz"); // false
validator.compare("abc", "abc"); // true
```

### Capturing the error messages
The `.onNotPass` event is executed when a rule fails at the time of its validation and returns its message.

```java
Validator validator = new Validator.Builder()
    .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
    .build();

// It is only executed if the validation of any rule fails
validator.onNotPass( message -> System.out.println(message) ); // "The text is different from 'xxx'"

validator.isValid("yyy"); // false
```

If you prefer not to work with the `.onNotPass` event, you can use the` .isValidOrFail` and `.compareOrFail` methods in
substitution of the `.isValid` and` .comapre` methods respectively.

The main difference is that these methods do not return any value and in case of failure they throw an exception of the type
`InvalidEvaluationException` that contains the error message of the rule together with the value of the String to evaluate.

```java
class Example {

    private Validator validator = new Validator.Builder()
            .rule("The text is different from 'xxx'", evaluate -> evaluate.equals("xxx"))
            .build();

    private void submit() {
        try {
            validator.isValidOrFail("yyy");
            // validator.compareOrFail("XXX", "YYY");
        } catch (InvalidEvaluationException e) {
            System.out.println(e.getMessage());
        }
        // TODO
    }

}
```

#### Predefined rules

Validator offers a series of predefined rules.

| Rule	              | Condition      | Message (`String`)  | Description                                                                                                  |
|---------------------|----------------|---------------------|--------------------------------------------------------------------------------------------------------------|
| `required`	      | No             | Optional            | Validate that the String to evaluate is different from empty and null                                        |
| `length`	      | Yes (`int`)    | Optional            | Validate that the String to evaluate has the exact length of characters to the condition                     |
| `minLength`	      | Yes (`int`)    | Optional            | Validate that the String to evaluate has a minimum character length to the condition                         |
| `maxLength`	      | Yes (`int`)    | Optional            | Validate that the String to evaluate has a maximum length of characters to the condition                     |
| `email`             | No             | Optional            | Validate that the String to evaluate has an email format                                                     |
| `numericFormat`     | No             | Optional            | Validate that the String to evaluate has a numeric format                                                    |
| `shouldOnlyContain` | Yes (`String`) | Optional            | Validate that the String to evaluate only contains characters included in the condition String               |
| `onlyNumbers`       | No             | Optional            | Validate that the String to evaluate contains only numeric characters                                        |
| `notContain`        | Yes (`String`) | Optional            | Validate that the String to evaluate does not contain any character included in the String of the condition  |
| `mustContainOne`    | Yes (`String`) | Optional            | Validate that the String to evaluate contains at least one character included in the String of the condition |

Predefined rules can simplify the definition of a Validator.
```java
final Validator validator = new Validator.Builder()
    .required("Requerido")
    .minLength(6, "Se requieren más caracteres")
    .onlyNumbers("Solo números")
    .build();
```

The message in the predefined rules is optional, as Validator offers default messages for each one.
```java
final Validator validator = new Validator.Builder()
    .required()
    .minLength(6)
    .onlyNumbers()
    .build();
```

The default messages are found in the `MessagesEn` classes for English messages, and in` MessagesEs`
for Spanish messages, both classes implement the `Messages` interface.

| Rule	              | English                                                  | Spanish                                               |
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

##### Note:
- Por defecto se muestran los mensajes en Ingles

### Cambiar los mensajes por defecto
Validator has a static method called `.setMessages` which receives an object of type` Messages` as a parameter.

```java
Validator.setMessages(new MessagesEs());
```

The `Messages` interface allows you to create custom default messages.

```java
Validator.setMessages(new Messages() {

    @Override
    public String getNotMatchMessage() { return "Personalized message"; }

    @Override
    public String getRequireMessage() { return "Personalized message"; }

    @Override
    public String getLengthMessage() { return "Personalized message"; }

    @Override
    public String getMinLengthMessage() { return "Personalized message"; }

    @Override
    public String getMaxLengthMessage() { return "Personalized message"; }

    @Override
    public String getEmailMessage() { return "Personalized message"; }

    @Override
    public String getNumericFormat() { return "Personalized message"; }

    @Override
    public String getShouldOnlyContainMessage() { return "Personalized message"; }

    @Override
    public String getOnlyNumbersMessage() { return "Personalized message"; }

    @Override
    public String getNotContainMessage() { return "Personalized message"; }

    @Override
    public String getMustContainOneMessage() { return "Personalized message"; }

});
```

##### Note:
- Messages declared next to a predefined rule take precedence over default messages.
- Predefined rules that require a `condition` parameter, make use of` String.format` to format the message with the 
  condition.

### Recommendations

Commonly, there are multiple instances of Strings to which to apply the same validation rules. For these cases
It is recommended to define the Validators by context, in order to define our Validator once and reuse it. Is
Logic is possible, since Validator includes the `.copy` method which generates copies of it.

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

    public Login() {
        emailValidator.onNotPass(System.out::println);
        passwordValidator.onNotPass(System.out::println);
    }

    public void submit() {
        if (!emailValidator.isValid(email) || !pswValidator.compare(psw, pswConfirmation)) return;
        // ...
    }

}
```
