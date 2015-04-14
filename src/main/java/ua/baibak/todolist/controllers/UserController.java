package ua.baibak.todolist.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.baibak.todolist.service.user.PasswordValidator;
import ua.baibak.todolist.entity.User;
import ua.baibak.todolist.service.user.UserServiceImpl;

import javax.inject.Inject;

@Controller
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class);
    @Inject
    private UserServiceImpl userService;
    @Inject
    private PasswordValidator passwordValidator;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout)throws Exception{
        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", "Invalid username and password!");
        }
        if (logout != null) {
            model.addObject("msg", "You've been logged out successfully.");
        }
        model.setViewName("login");
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registration() throws Exception{
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("userAdd", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("userAdd") @Validated User userForSaving, BindingResult result) throws Exception {
        passwordValidator.validate(userForSaving, result);
       if(userService.checkUserName(userForSaving.getUserName())== "yes"){
           throw new Exception("names already used");
       }
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("registration");
            modelAndView.addObject("userAdd", userForSaving);
            return modelAndView;
        } else {
            userService.createNewUser(userForSaving);
            return new ModelAndView("login");
        }
    }
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        if(ex.toString().equalsIgnoreCase("org.hibernate.TransactionException: Transaction not successfully started")) {
            System.exit(500);
        }
        log.error("Exception" + ex.toString());
        ModelAndView model = new ModelAndView("error");
        model.addObject("Exception", ex);
        return model;
    }
}
