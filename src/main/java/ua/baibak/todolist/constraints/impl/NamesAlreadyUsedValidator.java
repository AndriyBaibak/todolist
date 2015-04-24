package ua.baibak.todolist.constraints.impl;


import org.apache.log4j.Logger;
import ua.baibak.todolist.constraints.NamesAlreadyUsed;
import ua.baibak.todolist.service.user.UserEntityService;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NamesAlreadyUsedValidator implements
        ConstraintValidator<NamesAlreadyUsed, Object> {


    private static Logger log = Logger.getLogger(NamesAlreadyUsedValidator.class);
    @Inject
    private UserEntityService userEntityService;
    @Override
    public void initialize(NamesAlreadyUsed namesAlreadyUsed) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean check = true;

        try {
            check = (!userEntityService.validationUserName(o.toString()));
        } catch (Exception e) {

            log.error("Exception during inValid method" + e.toString());
        }
        return check;
    }
}
