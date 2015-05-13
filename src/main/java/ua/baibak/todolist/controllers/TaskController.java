package ua.baibak.todolist.controllers;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("#name == authentication.name")
    @RequestMapping(value = "/{name}/tasks", method = RequestMethod.GET)
    public ModelAndView allTasks(@PathVariable("name") String name) throws Exception {
        return generateModelForView("view", name);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ModelAndView all() throws Exception {
        return new ModelAndView("all");
    }

    @PreAuthorize("#name == authentication.name")
    @RequestMapping(value = "/{name}/task", method = RequestMethod.POST)
    public ModelAndView addTask(@ModelAttribute("taskForAdd") @Valid Task taskForSaving,@PathVariable("name") String name, BindingResult result) throws Exception {
        taskForSaving.setAuthor(name);
        if (result.hasErrors()) {
            return generateModelForView("view", taskForSaving, name);
        } else {
            taskService.createAndSaveNewTask(taskForSaving);
            return new ModelAndView("redirect:/" + name + "/tasks");
        }
    }

    @PreAuthorize("#name == authentication.name")
    @RequestMapping(value = "/{name}/task/{id}", method = RequestMethod.POST)
    public ModelAndView updateTask(@ModelAttribute("taskForUpdate") @Valid Task taskForUpdate,@PathVariable("name") String name, @PathVariable("id") String id) throws Exception {
        taskService.updateTasks(taskForUpdate, id);
        return new ModelAndView("redirect:/" + name + "/tasks");
    }

    @PreAuthorize("#name == authentication.name")
    @RequestMapping(value = "/{name}/deleteTask/{id}", method = RequestMethod.DELETE)
    public ModelAndView deleteTask(@PathVariable("id") String id, @PathVariable("name") String name) throws Exception {
        taskService.deleteTask(id);
        return new ModelAndView("redirect:/" + name + "/tasks");
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