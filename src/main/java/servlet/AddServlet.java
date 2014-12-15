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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Андрей on 24.11.2014.
 */
public class AddServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    public  Date dateDeadline = null;
    private RequestDispatcher dispatcherForException = null;
    public  SimpleDateFormat sp = new SimpleDateFormat("yyyy.MM.dd");

    public void init() throws ServletException {
        tasksDAO = new TasksDAOImpl();
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String taskDescription = request.getParameter("new_task");
        String taskDeadline = request.getParameter("deadline");
        try {
            dateDeadline = sp.parse(taskDeadline);
        }catch (ParseException e) {
           String exception = "Помилка при заддані дати виконання: " + e.toString();
           request.setAttribute("Exception", exception);
           dispatcherForException.forward(request, response);
        }
        Tasks newTask = new Tasks(taskDescription, dateDeadline);
        try {
            tasksDAO.save(newTask);
        } catch (Exception e) {
            String exception = "Помилка при збереженні завдання: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/added.jsp");
        rd.forward(request, response);
    }
}
