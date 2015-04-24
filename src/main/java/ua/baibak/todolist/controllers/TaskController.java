package ua.baibak.todolist.controllers;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.baibak.todolist.entity.Task;
import ua.baibak.todolist.service.task.TaskService;

import javax.inject.Inject;
import javax.validation.Valid;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    private static Logger log = Logger.getLogger(TaskController.class);

    @Inject
    private TaskService taskService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value = "/users/{name}/allTasks", method = RequestMethod.GET)
    public ModelAndView allTasks() throws Exception {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        return generateModelForView("view", author);
    }

    @RequestMapping(value = "/users/all", method = RequestMethod.GET)
    public ModelAndView all() throws Exception {
        return new ModelAndView("all");
    }

    @RequestMapping(value = "/users/{name}/addTask", method = RequestMethod.POST)
    public ModelAndView addTask(@ModelAttribute("taskForAdd") @Valid Task taskForSaving, BindingResult result) throws Exception {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        taskForSaving.setAuthor(author);
        if (result.hasErrors()) {
            return generateModelForView("view", taskForSaving, author);
        } else {
            taskService.createAndSaveNewTask(taskForSaving);
            return generateModelForView("view", author);
        }
    }


    @RequestMapping(value = "/users/{name}/updateTask/{id}", method = RequestMethod.POST)
    public ModelAndView updateTask(@ModelAttribute("taskForUpdate") @Valid Task taskForUpdate, BindingResult result, @PathVariable("id") String id) throws Exception {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        taskService.updateTasks(taskForUpdate, id);
        return generateModelForView("view", author);
    }

    @RequestMapping(value = "/users/{name}/deleteTask/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTask(@PathVariable("id") String id) throws Exception {
        String author = SecurityContextHolder.getContext().getAuthentication().getName();
        taskService.deleteTask(id);
        return generateModelForView("view", author);
    }

    private ModelAndView generateModelForView(String viewPage, String author) throws Exception {
        ModelAndView modelSuccess = new ModelAndView(viewPage);
        List tasks = taskService.getAllTasks(author);
        modelSuccess.addObject("tasks", tasks);
        modelSuccess.addObject("taskForAdd", new Task());
        modelSuccess.addObject("taskForUpdate", new Task());
        return modelSuccess;
    }

    private ModelAndView generateModelForView(String viewPage, Task taskForSaving, String author) throws Exception {
        List tasks = taskService.getAllTasks(author);
        ModelAndView model = new ModelAndView(viewPage, "taskForAdd", taskForSaving);
        model.addObject("tasks", tasks);
        model.addObject("taskForUpdate", new Task());
        return model;
    }
}