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
import javax.servlet.jsp.ErrorData;
import java.io.IOException;

public class AddServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(AddServlet.class);
    private WebApplicationContext ctx = null;
    private Validate beanForValidation = null;
    private TasksService beanForService = null;
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;
    private ErrorData errorData = null;

    public void init() throws ServletException {
        ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
        beanForValidation = beanForValidation = (Validate) ctx.getBean("validate");
        beanForService = beanForService = (TasksService) ctx.getBean("tasksService");
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
            String exception = "Помилка при збереженні завдання.";
            log.error(exception, e);
            dispatcherForException.forward(request, response);
        }
        dispatcherForAddTasks.forward(request, response);
    }
}
