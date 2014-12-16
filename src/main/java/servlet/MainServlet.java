package servlet;
import dao.Service;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MainServlet extends HttpServlet {

    protected List tasks = null;
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForShowTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForShowTasks = getServletContext().getRequestDispatcher("/index.jsp");
    }
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
           tasks = Service.getAllTasks();
        } catch (Exception e) {
            String exception = "Помилка при отриманні усіх наявних завдань: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        request.setAttribute("tasks", tasks);
        dispatcherForShowTasks.forward(request, response);
    }

}