package servlet;

import dao.TasksDAOImpl;
import entity.Tasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Андрей on 28.11.2014.
 */
public class DeleteServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    public Tasks taskForDelete = null;
    private RequestDispatcher dispatcherForException  = getServletContext().getRequestDispatcher("/error.jsp");


    public void init() throws ServletException {
        tasksDAO = new TasksDAOImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer idTask = Integer.parseInt(request.getParameter("id for delete"));
        try {
            taskForDelete = tasksDAO.getTasksById(idTask);
        } catch (Throwable e) {
            String exception = "Помилка при отриманні об'єкта за його id: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        System.out.println(idTask);
           //  System.out.println(taskForDelete.toString());
        try {
            tasksDAO.deleteTasks(taskForDelete);
        } catch (Throwable e) {
            String exception = "Помилка при видаленні об'єкта за його id: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/delete.jsp");
        rd.forward(request, response);
    }
}
