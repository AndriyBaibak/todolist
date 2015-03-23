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
import ua.baibak.todolist.entity.Task;
import ua.baibak.todolist.service.TaskService;
import ua.baibak.todolist.service.Validate;

import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    private static Logger log = Logger.getLogger(TaskController.class);
    private ApplicationContext ctx = new ClassPathXmlApplicationContext("springContext.xml");
    private TaskService objectForOperationsWithTask = null;
    private List tasks = new ArrayList();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value = "/allTask", method = RequestMethod.GET)
    public ModelAndView allTasks() {
        objectForOperationsWithTask = (TaskService) ctx.getBean("taskService");
        try {
            tasks = objectForOperationsWithTask.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception", ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("taskView", new Task());
        modelAndView.addObject("taskViewUpdate", new Task());
        return modelAndView;
    }

    @RequestMapping(value = "/addTask", method = RequestMethod.POST)
    public ModelAndView addTask(@ModelAttribute("taskView") @Valid Task taskForSaving, BindingResult result, @RequestParam("description") String description) throws Exception {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("view");
            return model;
        } else {
            objectForOperationsWithTask = (TaskService) ctx.getBean("taskService");
            try {
                objectForOperationsWithTask.createAndSaveNewTask(taskForSaving.getDescription(), taskForSaving.getDeadline());
                tasks = objectForOperationsWithTask.getAllTasks();
            } catch (Exception ex) {
                log.error("Exception" + ex);
                ModelAndView model = new ModelAndView("error");
                model.addObject("Exception", ex);
                return model;
            }
            ModelAndView modelAndView = new ModelAndView("view");
            modelAndView.addObject("tasks", tasks);
            modelAndView.addObject("taskView", new Task());
            modelAndView.addObject("taskViewUpdate", new Task());
            return modelAndView;
        }
    }

    @RequestMapping(value = "/updateTask/{id}", method = RequestMethod.POST)
    public ModelAndView updateTask(@ModelAttribute("taskViewUpdate") @Valid Task taskForUpdate, BindingResult result, @PathVariable("id") String id) throws Exception {

            objectForOperationsWithTask = (TaskService) ctx.getBean("taskService");
            try {
                objectForOperationsWithTask.updateTasks(taskForUpdate, id);
                tasks = objectForOperationsWithTask.getAllTasks();
            } catch (Exception ex) {
                log.error("Exception" + ex);
                ModelAndView model = new ModelAndView("error");
                model.addObject("Exception", ex);
                return model;
            }
            ModelAndView modelAndView = new ModelAndView("view");
            modelAndView.addObject("tasks", tasks);
            modelAndView.addObject("taskView", new Task());
            modelAndView.addObject("taskViewUpdate", new Task());
            return modelAndView;
        }

    @RequestMapping(value = "/deleteTask/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTask(@PathVariable("id") String id) throws Exception {
        objectForOperationsWithTask = (TaskService) ctx.getBean("taskService");
        try {
            objectForOperationsWithTask.deleteTask(id);
            tasks = objectForOperationsWithTask.getAllTasks();
        } catch (Exception ex) {
            log.error("Exception" + ex);
            ModelAndView model = new ModelAndView("error");
            model.addObject("Exception", ex);
            return model;
        }
        ModelAndView modelAndView = new ModelAndView("view");
        modelAndView.addObject("tasks", tasks);
        modelAndView.addObject("taskView", new Task());
        modelAndView.addObject("taskViewUpdate", new Task());
        return modelAndView;
    }
}