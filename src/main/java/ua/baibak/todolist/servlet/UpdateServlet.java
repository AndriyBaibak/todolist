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

public class UpdateServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(AddServlet.class);
    private WebApplicationContext ctx = null;
    private TasksService beanForService = null;
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;

    public void init() throws ServletException {
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        beanForService = (TasksService) ctx.getBean("tasksService");
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/todolist/");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String newData = request.getParameter("newData");
        String idTask = request.getParameter("id");
        String type = request.getParameter("type");
        try {
            beanForService.updateTasks(newData, idTask, type);
        } catch (Exception e) {
            String exception = "Помилка при змінні завдання: " + e.toString();
            request.setAttribute("Exception", exception);
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }
        dispatcherForAddTasks.forward(request, response);

    }
}
