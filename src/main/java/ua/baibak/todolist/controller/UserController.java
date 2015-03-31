package ua.baibak.todolist.controller;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
                              @RequestParam(value = "logout", required = false) String logout) {

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

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        modelAndView.addObject("userAdd", new User());
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registering(@ModelAttribute("userAdd") @Valid User userForSaving, BindingResult result) {
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("register");
            modelAndView.addObject("userAdd", userForSaving);
            return modelAndView;
        } else {
            try {
                userService.createNewUser(userForSaving);
            } catch (Exception exception) {
                log.error("Exception" + exception);
                ModelAndView modelError = new ModelAndView("error");
                modelError.addObject("exception", exception);
                modelError.addObject("exceptionMessage", exception.getMessage());
                return modelError;
            }
            return new ModelAndView("login");
        }
    }
}
