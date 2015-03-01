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
import java.util.List;

public class MainServlet extends HttpServlet {

    private WebApplicationContext ctx = null;
    private TasksService objectForService = null;
    private static Logger log = Logger.getLogger(MainServlet.class);
    private List tasks = null;
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForShowTasks = null;

    public void init() throws ServletException {
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        objectForService =  (TasksService) ctx.getBean("tasksService");
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForShowTasks = getServletContext().getRequestDispatcher("/view.jsp");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            tasks = objectForService.getAllTasks();
        } catch (Exception e) {
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }
        request.setAttribute("tasks", tasks);
        dispatcherForShowTasks.forward(request, response);

    }


}