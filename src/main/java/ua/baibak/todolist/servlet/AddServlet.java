package ua.baibak.todolist.servlet;


import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ua.baibak.todolist.service.TasksService;
import ua.baibak.todolist.service.Validate;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(AddServlet.class);
    private WebApplicationContext ctx = null;
    private TasksService beanForService = null;
    private Validate beanForValidation = null;
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;

    public void init() throws ServletException {
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        beanForService = (TasksService) ctx.getBean("tasksService");
        beanForValidation = (Validate) ctx.getBean("validation");
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/todolist");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String taskDescription = request.getParameter("newTask");
        String taskDeadline = request.getParameter("calendar");
        try {
            beanForValidation.validateTaskData(taskDescription, taskDeadline);
            beanForService.createAndSaveNewTask(taskDescription, taskDeadline);
        } catch (Exception e) {
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }
        dispatcherForAddTasks.forward(request, response);
    }
}
