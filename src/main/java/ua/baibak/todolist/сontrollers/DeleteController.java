package ua.baibak.todolist.сontrollers;


import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;
import org.springframework.web.servlet.view.RedirectView;
import ua.baibak.todolist.service.TasksService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteController extends AbstractController {

    private WebApplicationContext ctx = null;
    private TasksService objectForService = null;
    private static Logger log = Logger.getLogger(DeleteController.class);

    public void initializedContextDelete(){
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        objectForService = (TasksService) ctx.getBean("tasksService");
    }

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        ModelAndView model = null;
        String idForDelete = httpServletRequest.getParameter("id for delete");
        try {
            objectForService.deleteTask(idForDelete);
        } catch (Exception e) {
            model = new ModelAndView("error");
            log.error("Exception", e);
            return model;
        }
        model = new ModelAndView(new RedirectView("main"));
        return model;
    }


}
