package servlet;

import dao.TasksDAOImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by Андрей on 20.11.2014.
 */
public class MainServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    List tasks = null;

    public void init() throws ServletException {
        tasksDAO = new TasksDAOImpl();
    }

    public void doGet (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        try {
            tasks = tasksDAO.getAllTasks();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("tasks", tasks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);


    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            tasks = tasksDAO.getAllTasks();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.setAttribute("tasks", tasks);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/index.jsp");
        rd.forward(request, response);


    }
}