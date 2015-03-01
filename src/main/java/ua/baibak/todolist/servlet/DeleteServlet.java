package ua.baibak.todolist.servlet;


import org.apache.log4j.Logger;
import ua.baibak.todolist.service123.TasksService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(DeleteServlet.class);
    public RequestDispatcher dispatcherForException = null;
    public RequestDispatcher dispatcherForDeleteTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForDeleteTasks = getServletContext().getRequestDispatcher("/todolist");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idForDelete = request.getParameter("id for delete");
        try {
            synchronized (this) {
                TasksService.getObjectToActionTasks().deleteTask(idForDelete);
            }
        } catch (Exception e) {
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }

        dispatcherForDeleteTasks.forward(request, response);
    }
}
