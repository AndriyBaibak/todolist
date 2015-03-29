package ua.baibak.todolist.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.baibak.todolist.entity.Task;
import ua.baibak.todolist.service.TaskService;

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
    private TaskService objectForActionWithTask;
    private List tasks = new ArrayList();

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value = "/success/allTasks", method = RequestMethod.GET)
    public ModelAndView allTasks() {
        return this.generateModelForView("view");
    }

    @RequestMapping(value = "/success/addTask", method = RequestMethod.POST)
    public ModelAndView addTask(@ModelAttribute("taskForAdd") @Valid Task taskForSaving, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            ModelAndView model = new ModelAndView("view", "taskForSaving", taskForSaving);
            model.addObject("tasks", tasks);
            model.addObject("taskForUpdate", new Task());
            return model;
        } else {
            try {
                objectForActionWithTask.createAndSaveNewTask(taskForSaving);
            } catch (Exception ex) {
                return this.catcheError(ex);
            }
            return this.generateModelForView("view");
        }
    }

    @RequestMapping(value = "/success/updateTask/{id}", method = RequestMethod.POST)
    public ModelAndView updateTask(@ModelAttribute("taskForUpdate") @Valid Task taskForUpdate, BindingResult result, @PathVariable("id") String id) throws Exception {
        try {
            objectForActionWithTask.updateTasks(taskForUpdate, id);
        } catch (Exception ex) {
            return this.catcheError(ex);
        }
        return this.generateModelForView("view");
    }

    @RequestMapping(value = "/success/deleteTask/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTask(@PathVariable("id") String id) throws Exception {
        try {
            objectForActionWithTask.deleteTask(id);
        } catch (Exception someException) {
            return this.catcheError(someException);
        }
        return this.generateModelForView("view");
    }

    private ModelAndView generateModelForView(String nameJspPage) {
        ModelAndView modelSuccess = new ModelAndView(nameJspPage);
        try {
            tasks = objectForActionWithTask.getAllTasks();

        } catch (Exception e) {
            this.catcheError(e);
        }
        modelSuccess.addObject("tasks", tasks);
        modelSuccess.addObject("taskForAdd", new Task());
        modelSuccess.addObject("taskForUpdate", new Task());
        return modelSuccess;
    }

    private ModelAndView catcheError(Exception exception) {
        log.error("Exception" + exception);
        ModelAndView modelError = new ModelAndView("error");
        modelError.addObject("exception", exception);
        modelError.addObject("exceptionMessage", exception.getMessage());
        return modelError;
    }

}