package ua.baibak.todolist.сontrollers;

import org.apache.log4j.Logger;
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
    private TasksService objectForService = null;
    private static Logger log = Logger.getLogger(MainController.class);
    private List tasks = null;


    public void initializationContextMain(){
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        objectForService = (TasksService) ctx.getBean("tasksService");
    }


    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request,
                                                 HttpServletResponse response) throws Exception {

        ModelAndView model= null;
        try {
            tasks = objectForService.getAllTasks();
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