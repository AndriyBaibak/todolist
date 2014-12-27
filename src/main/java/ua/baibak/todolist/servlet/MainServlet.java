package ua.baibak.todolist.servlet;
import ua.baibak.todolist.Service.TasksService;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(MainServlet.class);
    protected List tasks = null;
    protected TasksService someworks = new TasksService();
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForShowTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForShowTasks = getServletContext().getRequestDispatcher("/index.jsp");
    }
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
           tasks = someworks.getAllTasks();
        } catch (Exception e) {
            String exception = "Помилка при отриманні усіх наявних завдань: " + e.toString();
            request.setAttribute("Exception", exception);
            log.debug("Exception", e);
            dispatcherForException.forward(request, response);
        }
        request.setAttribute("tasks", tasks);
        dispatcherForShowTasks.forward(request, response);

    }


}