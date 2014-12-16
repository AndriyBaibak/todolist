package servlet;


import dao.Service;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteServlet extends HttpServlet {

    public RequestDispatcher dispatcherForException  = null;
    public RequestDispatcher dispatcherForDeleteTasks = null;

    public void init() throws ServletException {
        dispatcherForException = getServletContext().getRequestDispatcher("/error.jsp");
        dispatcherForDeleteTasks = getServletContext().getRequestDispatcher("/delete.jsp");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idForDelete = request.getParameter("id for delete");
        try {
            Service.deleteTask(idForDelete);
        } catch (Exception e) {
            String exception = "Помилка при видаленні об'єкта за його id: " + e.toString();
            request.setAttribute("Exception", exception);
            dispatcherForException.forward(request, response);
        }
        dispatcherForDeleteTasks.forward(request, response);
    }
}
