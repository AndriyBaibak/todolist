package ua.baibak.todolist.servlet;

import org.apache.log4j.Logger;
import ua.baibak.todolist.service.TasksService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by Андрей on 20.01.2015.
 */
public class UpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(AddServlet.class);
    private TasksService objectToActionTasks = new TasksService();
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/todolist");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String newDescription = request.getParameter("newDescription");
        String idTask = request.getParameter("id");
        System.out.println("in updateservlet------------");
        System.out.println(newDescription);
        System.out.println(idTask);

        try {
            objectToActionTasks.updateTasks(newDescription,idTask);
        } catch (Exception e) {
            String exception = "Помилка при змінні завдання: " + e.toString();
            request.setAttribute("Exception", exception);
            log.debug("Exception", e);
            dispatcherForException.forward(request, response);
        }

        dispatcherForAddTasks.forward(request, response);

    }
}
