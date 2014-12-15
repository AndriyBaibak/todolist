package servlet;

import dao.TasksDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Андрей on 20.11.2014.
 */
public class MainServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    protected List tasks = null;
    private RequestDispatcher dispatcherForException = null;

    public void init() throws ServletException {

        tasksDAO = new TasksDAOImpl();
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            tasks = tasksDAO.getAllTasks();
        } catch (Exception e) {
            String exception = "Помилка при отриманні усіх наявних завдань: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        request.setAttribute("tasks", tasks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            tasks = tasksDAO.getAllTasks();
        } catch (Exception e) {
            String exception = "Помилка при отриманні усіх наявних завдань: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        request.setAttribute("tasks", tasks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);
    }
}