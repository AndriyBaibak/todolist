package ua.baibak.todolist.controllers.validation.impl;

import org.apache.log4j.Logger;
import ua.baibak.todolist.service.user.TransactionUserService;
import ua.baibak.todolist.controllers.validation.NamesAlreadyUsed;

import javax.inject.Inject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NameAlreadyUsedValidator implements
        ConstraintValidator<NamesAlreadyUsed, Object> {

    private static Logger log = Logger.getLogger(NameAlreadyUsedValidator.class);
    @Inject
    private TransactionUserService userEntityService;

    @Override
    public void initialize(NamesAlreadyUsed namesAlreadyUsed) {
    }

    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        boolean check = true;
        try {
            check = (!userEntityService.checkUniqueUserName(o.toString()));
        } catch (Exception e) {
            log.error("Exception during inValid method" + e.toString());
        }
        return check;
    }
}
