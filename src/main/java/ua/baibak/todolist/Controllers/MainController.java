package ua.baibak.todolist.Controllers;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import ua.baibak.todolist.service.TasksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


public class MainController extends AbstractController {

    private WebApplicationContext ctx = null;
    private TasksService beanForService = null;
    private static Logger log = Logger.getLogger(MainController.class);
    private List tasks = null;


    public void initializationContextMain(){
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        beanForService = (TasksService) ctx.getBean("tasksService");
    }


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        ModelAndView model= null;
        try {
            tasks = beanForService.getAllTasks();
        } catch (Exception e) {
            model = new ModelAndView("error");
            model.addObject("error", e);
            log.error("Exception", e);
            return model;
        }
        model = new ModelAndView("view");
        model.addObject("tasks", tasks);
        return model;
    }
}