package ua.baibak.todolist.controllers;


import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {
    private static Logger log = Logger.getLogger(GlobalExceptionController.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        if (ex.toString().equalsIgnoreCase("org.hibernate.TransactionException: Transaction not successfully started")) {
            System.exit(500);
        }
        log.error("Exception" + ex.toString());
        ModelAndView model = new ModelAndView("error");
        model.addObject("Exception", ex);
        return model;
    }

}