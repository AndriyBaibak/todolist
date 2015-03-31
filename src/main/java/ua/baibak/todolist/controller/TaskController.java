package ua.baibak.todolist.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.propertyeditors.CustomDateEditor;
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

    @RequestMapping(value = "/success/allTasks", method = RequestMethod.GET)
    public ModelAndView allTasks() {
        return generateModelForView("view");
    }

    @RequestMapping(value = "/success/addTask", method = RequestMethod.POST)
    public ModelAndView addTask(@ModelAttribute("taskForAdd") @Valid Task taskForSaving, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return generateModelForView("view",taskForSaving);
        } else {
            try {
                taskService.createAndSaveNewTask(taskForSaving);
            } catch (Exception ex) {
                return catchError(ex);
            }
            return generateModelForView("view");
        }
    }

    @RequestMapping(value = "/success/updateTask/{id}", method = RequestMethod.POST)
    public ModelAndView updateTask(@ModelAttribute("taskForUpdate") @Valid Task taskForUpdate, BindingResult result, @PathVariable("id") String id) throws Exception {
        try {
            taskService.updateTasks(taskForUpdate, id);
        } catch (Exception ex) {
            return catchError(ex);
        }
        return generateModelForView("view");
    }

    @RequestMapping(value = "/success/deleteTask/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTask(@PathVariable("id") String id) throws Exception {
        try {
            taskService.deleteTask(id);
        } catch (Exception someException) {
            return catchError(someException);
        }
        return generateModelForView("view");
    }


    private ModelAndView generateModelForView(String viewPage) {
        ModelAndView modelSuccess = new ModelAndView(viewPage);
        List tasks = new ArrayList<Task>();
        try {
            tasks = taskService.getAllTasks();

        } catch (Exception e) {
            catchError(e);
        }
        modelSuccess.addObject("tasks", tasks);
        modelSuccess.addObject("taskForAdd", new Task());
        modelSuccess.addObject("taskForUpdate", new Task());
        return modelSuccess;
    }
    private ModelAndView generateModelForView(String viewPage, Task taskForSaving){
        List tasks = new ArrayList<Task>();
        try {
            tasks = taskService.getAllTasks();
        } catch (Exception e) {
            catchError(e);
        }
        ModelAndView model = new ModelAndView(viewPage, "taskForAdd", taskForSaving);
        model.addObject("tasks", tasks);
        model.addObject("taskForUpdate", new Task());
        return model;
    }

    private ModelAndView catchError(Exception exception) {
        log.error("Exception" + exception);
        ModelAndView modelError = new ModelAndView("error");
        modelError.addObject("exception", exception);
        modelError.addObject("exceptionMessage", exception.getMessage());
        return modelError;
    }

}