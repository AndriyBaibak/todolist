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

public class AddServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    private RequestDispatcher dispatcherForException = null;
    private RequestDispatcher dispatcherForAddTasks = null;
    public  SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");

    public void init() throws ServletException {
        tasksDAO = new TasksDAOImpl();
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForAddTasks = getServletContext().getRequestDispatcher("/added.jsp");
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Tasks newTask = null;
        request.setCharacterEncoding("UTF-8");//чомусь без цієї стрічки не читає киррилицю
        System.out.print(request.getParameter("date"));
        String taskDescription = request.getParameter("newTask");
        String taskDeadline = request.getParameter("date");
        try {
            Date dateDeadline = sp.parse(taskDeadline);
            newTask = new Tasks(taskDescription, dateDeadline);
        }catch (ParseException e) {
           String exception = "Помилка при заддані дати виконання: " + e.toString();
           request.setAttribute("Exception", exception);
           dispatcherForException.forward(request, response);
        }

        try {
            tasksDAO.save(newTask);
        } catch (Exception e) {
            String exception = "Помилка при збереженні завдання: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        dispatcherForAddTasks.forward(request, response);
    }
}
