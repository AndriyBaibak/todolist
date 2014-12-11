package servlet;

import dao.TasksDAOImpl;
import entity.Tasks;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Андрей on 24.11.2014.
 */
public class AddServlet extends HttpServlet {
    private TasksDAOImpl tasksDAO;
    Date date_deadline = null;

    public void init() throws ServletException {
        tasksDAO = new TasksDAOImpl();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String task_description = request.getParameter("new_task");
        String task_deadline = request.getParameter("deadline");
        SimpleDateFormat sp = new SimpleDateFormat("yyyy.MM.dd");


        try {
            date_deadline = sp.parse(task_deadline);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Tasks task = new Tasks(task_description, date_deadline);
        System.out.println(task.toString());
        tasksDAO.save(task);
        RequestDispatcher rd = getServletContext().getRequestDispatcher("/added.jsp");
        rd.forward(request, response);
    }
}
