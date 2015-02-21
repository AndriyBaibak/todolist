package ua.baibak.todolist.servlet;

import org.apache.log4j.Logger;
import ua.baibak.todolist.service.TasksService;
import ua.baibak.todolist.service.Validate;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends HttpServlet implements Servlet {
    private static Logger log = Logger.getLogger(AddServlet.class);
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/todolist");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String taskDescription = request.getParameter("newTask");
        String taskDeadline = request.getParameter("calendar");
        try {
            Validate.validateTaskData(taskDescription, taskDeadline);
            TasksService.getObjectToActionTasks().createAndSaveNewTask(taskDescription, taskDeadline);
        } catch (Exception e) {
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }

        dispatcherForAddTasks.forward(request, response);
    }
}
