package servlet;


import dao.Service;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends javax.servlet.http.HttpServlet implements javax.servlet.Servlet {
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(AddServlet.class);


    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/added.jsp");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String taskDescription = request.getParameter("newTask");
        String taskDeadline = request.getParameter("date");
        try {
            Service.createAndSaveNewTask(taskDeadline, taskDescription);
        } catch (Exception e) {
            String exception = "Помилка при збереженні завдання: " + e.toString();
            request.setAttribute("Exception", exception);
            log.debug("Exception", e);
            dispatcherForException.forward(request, response);
        }

        dispatcherForAddTasks.forward(request, response);
    }
}
