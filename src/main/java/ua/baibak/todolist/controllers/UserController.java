package ua.baibak.todolist.controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ua.baibak.todolist.entity.User;
import ua.baibak.todolist.service.user.UserServiceImpl;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller
public class UserController {
    private static Logger log = Logger.getLogger(UserController.class);
    @Inject
    private UserServiceImpl userService;


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
                              @RequestParam(value = "logout", required = false) String logout) throws Exception {
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
    public ModelAndView registration() throws Exception {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration");
        modelAndView.addObject("userAdd", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@Valid User userAdd, BindingResult result) throws Exception {
        if (userService.checkUserName(userAdd.getUserName())) {
            throw new Exception("names already used");
        }
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("registration");
            modelAndView.addObject("userAdd", userAdd);
            return modelAndView;
        } else {
            userService.createNewUser(userAdd);
            return new ModelAndView("login");
        }
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        if (ex instanceof org.hibernate.TransactionException) {
            System.exit(500);
        }
        log.error("Exception" + ex.toString());
        ModelAndView model = new ModelAndView("error");
        model.addObject("Exception", ex);
        return model;
    }
}
