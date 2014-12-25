package servlet;


import dao.TasksService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(DeleteServlet.class);
    protected TasksService someworks = new TasksService();
    public RequestDispatcher dispatcherForException  = null;
    public RequestDispatcher dispatcherForDeleteTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForDeleteTasks = getServletContext().getRequestDispatcher("/todolist");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idForDelete = request.getParameter("id for delete");
        try {
            someworks.deleteTask(idForDelete);
        } catch (Exception e) {
            String exception = "Помилка при видаленні об'єкта за його id: " + e.toString();
            request.setAttribute("Exception", exception);
            log.debug("Exception", e);
            dispatcherForException.forward(request, response);
        }

        dispatcherForDeleteTasks.forward(request, response);
    }
}
