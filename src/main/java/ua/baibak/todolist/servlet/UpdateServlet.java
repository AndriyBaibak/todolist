package ua.baibak.todolist.servlet;

import org.apache.log4j.Logger;
import ua.baibak.todolist.service123.TasksService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateServlet extends HttpServlet {

    private static Logger log = Logger.getLogger(AddServlet.class);
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/todolist");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String newData = request.getParameter("newData");
        String idTask = request.getParameter("id");
        String type = request.getParameter("type");
        try {
            synchronized (this) {
                TasksService.getObjectToActionTasks().updateTasks(newData, idTask, type);
            }
        } catch (Exception e) {
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }

        dispatcherForAddTasks.forward(request, response);

    }
}
