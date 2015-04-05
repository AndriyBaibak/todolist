package ua.baibak.todolist.service.user;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ua.baibak.todolist.entity.User;

public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.empty", "Password must not be empty.");
        if (!(user.getPassword()).equals(user
                .getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirmPassword.passwordDontMatch", "Passwords don't match.");
        }

    }
}
