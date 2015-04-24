package ua.baibak.todolist.constraints;
import ua.baibak.todolist.constraints.impl.FieldEqualsValidator;
import ua.baibak.todolist.constraints.impl.NamesAlreadyUsedValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(ElementType.FIELD)
@Retention(RUNTIME)
@Constraint(validatedBy = NamesAlreadyUsedValidator.class)
@Documented
public @interface NamesAlreadyUsed {
    public static final String MESSAGE = "User Name already used. Please choose another name.";

    String message() default MESSAGE;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
