package ua.baibak.todolist.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.service.TasksService;
import ua.baibak.todolist.service.Validate;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TasksController {
    private static Logger log = Logger.getLogger(TasksController.class);
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("springContext.xml");
    private TasksService objectForAction = null;
    private Validate objectForValidation = null;
    private List tasks = new ArrayList();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

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
        modelAndView.addObject("tasksView", new Tasks());
        return modelAndView;
    }

    @RequestMapping(value = "/allTasks", method = RequestMethod.POST)
    public ModelAndView addTasks(@ModelAttribute Tasks taskForSaving, BindingResult result) throws Exception {
        objectForValidation = (Validate) ctx.getBean("validate");
        objectForAction = (TasksService) ctx.getBean("tasksService");
       try {
            objectForAction.createAndSaveNewTask(taskForSaving.getDescription(), taskForSaving.getDeadline());
            tasks = objectForAction.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception" + ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("tasksView", new Tasks());
        return modelAndView;
    }

    @RequestMapping(value = "/updateTasks/{newData}/{idTask}/{type}", method = RequestMethod.POST)
    public ModelAndView updateTasks(@PathVariable ("newData") String newData, @PathVariable ("idTask") String idTask, @PathVariable ("type") String type) throws Exception {
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
        modelAndView.addObject("tasksView", new Tasks());
        return modelAndView;
    }

    @RequestMapping(value = "/deleteTasks", method = RequestMethod.GET)
    public ModelAndView deleteTasks(@RequestParam("id for delete") String id) throws Exception {
        objectForAction = (TasksService) ctx.getBean("tasksService");
        try {
            objectForAction.deleteTask(id);
            tasks = objectForAction.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception" + ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("tasksView", new Tasks());
        return modelAndView;
    }
}