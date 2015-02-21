package ua.baibak.todolist.servlet;


import ua.baibak.todolist.service.TasksService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.ErrorData;
import java.io.IOException;

public class AddServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(AddServlet.class);
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;
    private ErrorData errorData = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/todolist");
    }
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String taskDescription = request.getParameter("newTask");
        String taskDeadline = request.getParameter("calendar");

        try {
            if(taskDescription.equals("")){
                throw new Exception("description empty");
            }
           TasksService.getObjectToActionTasks().createAndSaveNewTask(taskDeadline, taskDescription);
        } catch (Exception e) {
            String exception = "Помилка при збереженні завдання: " + e.toString();
            response.setStatus(406);
            errorData = new ErrorData(e,406,request.getRequestURI(),getServletName());
            System.out.println(errorData.getStatusCode());
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }
        dispatcherForAddTasks.forward(request, response);
    }
}
