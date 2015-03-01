package ua.baibak.todolist.servlet;

import org.apache.log4j.Logger;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.service.TasksService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainServlet extends HttpServlet {
    private static Logger log = Logger.getLogger(MainServlet.class);
    private List tasks = new CopyOnWriteArrayList<Tasks>();
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForShowTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForShowTasks = getServletContext().getRequestDispatcher("/view.jsp");
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            synchronized (this) {
                tasks = TasksService.getObjectToActionTasks().getAllTasks();
            }
        } catch (Exception e) {
            log.error("Exception", e);
            dispatcherForException.forward(request, response);
        }
        request.setAttribute("tasks", tasks);
        dispatcherForShowTasks.forward(request, response);

    }


}