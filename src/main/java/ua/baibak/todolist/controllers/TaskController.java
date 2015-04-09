package ua.baibak.todolist.controllers;

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
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {
    @Inject
    private TaskService taskService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
    }

    @RequestMapping(value = "/authorized/allTasks", method = RequestMethod.GET)
    public ModelAndView allTasks() throws Exception {
        return generateModelForView("view");
    }

    @RequestMapping(value = "/authorized/addTask", method = RequestMethod.POST)
    public ModelAndView addTask(@ModelAttribute("taskForAdd") @Valid Task taskForSaving, BindingResult result) throws Exception {
        if (result.hasErrors()) {
            return generateModelForView("view", taskForSaving);
        } else {
            taskService.createAndSaveNewTask(taskForSaving);
            return generateModelForView("view");
        }
    }

    @RequestMapping(value = "/authorized/updateTask/{id}", method = RequestMethod.POST)
    public ModelAndView updateTask(@ModelAttribute("taskForUpdate") @Valid Task taskForUpdate, BindingResult result, @PathVariable("id") String id) throws Exception {
        taskService.updateTasks(taskForUpdate, id);
        return generateModelForView("view");
    }

    @RequestMapping(value = "/authorized/deleteTask/{id}", method = RequestMethod.POST)
    public ModelAndView deleteTask(@PathVariable("id") String id) throws Exception {
        taskService.deleteTask(id);
        return generateModelForView("view");
    }

    private ModelAndView generateModelForView(String viewPage) throws Exception {
        ModelAndView modelSuccess = new ModelAndView(viewPage);
        List tasks = taskService.getAllTasks();
        modelSuccess.addObject("tasks", tasks);
        modelSuccess.addObject("taskForAdd", new Task());
        modelSuccess.addObject("taskForUpdate", new Task());
        return modelSuccess;
    }

    private ModelAndView generateModelForView(String viewPage, Task taskForSaving) throws Exception {
        List tasks =  taskService.getAllTasks();
        ModelAndView model = new ModelAndView(viewPage, "taskForAdd", taskForSaving);
        model.addObject("tasks", tasks);
        model.addObject("taskForUpdate", new Task());
        return model;
    }
}