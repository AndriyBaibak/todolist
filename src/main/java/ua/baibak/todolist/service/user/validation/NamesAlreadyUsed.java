package ua.baibak.todolist.service.user.validation;


import ua.baibak.todolist.service.user.validation.impl.NameAlreadyUsedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NameAlreadyUsedValidator.class)
@Documented
public @interface NamesAlreadyUsed {
    public static final String MESSAGE = "User Name already used. Please choose another name.";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
