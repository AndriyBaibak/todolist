package ua.baibak.todolist.controller;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ua.baibak.todolist.service.TasksService;
import ua.baibak.todolist.service.Validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {
    private static Logger log = Logger.getLogger(MainController.class);
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("springContext.xml");
    private TasksService objectForAction = null;
    private Validate objectForValidation = null;
    private List tasks = new ArrayList();

    @RequestMapping(value = "/allTasks", method = RequestMethod.GET)
    public ModelAndView tasks() {
        objectForAction = (TasksService) ctx.getBean("tasksService");
        try {
            tasks = objectForAction.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception", ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }

    @RequestMapping(value = "/allTasks", method = RequestMethod.POST)
    public ModelAndView addTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String descriptionForNewTask = request.getParameter("newTask");
        String deadlineForNewTask = request.getParameter("calendar");
        objectForValidation = (Validate) ctx.getBean("validate");
        objectForAction = (TasksService) ctx.getBean("tasksService");
        try {
            objectForValidation.validateTaskData(descriptionForNewTask, deadlineForNewTask);
            objectForAction.createAndSaveNewTask(descriptionForNewTask, deadlineForNewTask);
            tasks = objectForAction.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception" + ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }

    @RequestMapping(value = "/updateTasks", method = RequestMethod.POST)
    public ModelAndView updateTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("UTF-8");
        String type = request.getParameter("type");
        String newData = request.getParameter("newData");
        String idTask = request.getParameter("id");
        objectForAction = (TasksService) ctx.getBean("tasksService");
        try {
            objectForAction.updateTasks(newData, idTask, type);
            tasks = objectForAction.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception" + ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }

    @RequestMapping(value = "/deleteTasks", method = RequestMethod.POST)
    public ModelAndView deleteTasks(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String idForDelete = request.getParameter("id for delete");
        objectForAction = (TasksService) ctx.getBean("tasksService");
        try {
            objectForAction.deleteTask(idForDelete);
            tasks = objectForAction.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception" + ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        return modelAndView;
    }
}