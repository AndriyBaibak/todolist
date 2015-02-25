package ua.baibak.todolist.Controllers;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;
import ua.baibak.todolist.service.TasksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateController extends AbstractController {

    private WebApplicationContext ctx = null;
    private TasksService beanForService = null;
    private static Logger log = Logger.getLogger(AddController.class);

    public void initializationContextUpdate(){
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        beanForService = (TasksService) ctx.getBean("tasksService");
    }

    public ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView model;
        httpServletRequest.setCharacterEncoding("UTF-8");
        String newData = httpServletRequest.getParameter("newData");
        String idTask = httpServletRequest.getParameter("id");
        String type = httpServletRequest.getParameter("type");
        try {
            beanForService.updateTasks(newData, idTask, type);
        } catch (Exception e) {
            model = new ModelAndView("error");
            log.error("Exception", e);
            return model;
        }
        model = new ModelAndView(new RedirectView("main.htm"));
        return model;
    }
}
