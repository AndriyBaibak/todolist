package servlet;

import dao.TasksDAOImpl;
import entity.Tasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Андрей on 28.11.2014.
 */
public class DeleteServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    Tasks task_for_delete = null;


    public void init() throws ServletException {
        tasksDAO = new TasksDAOImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Integer id_task = Integer.parseInt(request.getParameter("id for delete"));
        try {
            task_for_delete = tasksDAO.getTasksById(id_task);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            tasksDAO.deleteTasks(task_for_delete);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/delete.jsp");
        rd.forward(request, response);
    }
}
