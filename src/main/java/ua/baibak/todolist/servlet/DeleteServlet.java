package ua.baibak.todolist.servlet;


import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.baibak.todolist.service.TasksService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private WebApplicationContext ctx = null;
    private TasksService beanForService = null;
    private static Logger log = Logger.getLogger(DeleteServlet.class);
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForDeleteTasks = null;

    public void init() throws ServletException {
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        beanForService = (TasksService) ctx.getBean("tasksService");
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForDeleteTasks = getServletContext().getRequestDispatcher("/todolist");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idForDelete = request.getParameter("id for delete");
        try {
            beanForService.deleteTask(idForDelete);
        } catch (Exception e) {
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }
        dispatcherForDeleteTasks.forward(request, response);
    }
}
