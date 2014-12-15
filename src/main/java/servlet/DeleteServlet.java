package servlet;

import dao.TasksDAOImpl;
import entity.Tasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    public Tasks taskForDelete = null;
    public RequestDispatcher dispatcherForException  = null;
    public RequestDispatcher dispatcherForDeleteTasks = null;
    public void init() throws ServletException {
        tasksDAO = new TasksDAOImpl();
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForDeleteTasks = getServletContext().getRequestDispatcher("/delete.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idTask = Integer.parseInt(request.getParameter("id for delete"));
        try {
            taskForDelete = tasksDAO.getTasksById(idTask);
        } catch (Exception e) {
            String exception = "Помилка при отриманні об'єкта за його id: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        System.out.println(idTask);
        try {
            tasksDAO.deleteTasks(taskForDelete);
        } catch (Exception e) {
            String exception = "Помилка при видаленні об'єкта за його id: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        dispatcherForDeleteTasks.forward(request, response);
    }
}
