package ua.baibak.todolist.Controllers;


import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.view.RedirectView;
import ua.baibak.todolist.service.TasksService;
import ua.baibak.todolist.service.Validate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddController extends SimpleFormController {

    private static Logger log = Logger.getLogger(AddController.class);
    private WebApplicationContext ctx = null;
    private TasksService beanForService = null;
    private Validate beanForValidation = null;

    public void initializationContextAdd(){
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        beanForService = (TasksService) ctx.getBean("tasksService");
        beanForValidation = (Validate) ctx.getBean("validation");

    }


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {
        ModelAndView model = null;
        request.setCharacterEncoding("UTF-8");
        String taskDescription = request.getParameter("newTask");
        String taskDeadline = request.getParameter("calendar");
        try {
            beanForValidation.validateTaskData(taskDescription, taskDeadline);
            beanForService.createAndSaveNewTask(taskDescription, taskDeadline);
        } catch (Exception e) {
            log.error("Exception", e);
            model = new ModelAndView("error");
            model.addObject("error", e);
            return model;
        }
        model = new ModelAndView(new RedirectView("main.htm"));
        return  model;


    }
}

