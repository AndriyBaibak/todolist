package dao;

import entity.Tasks;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Андрей on 16.12.2014.
 */
public class Service {

    public static HibernateTasksDAO hibernateTasksDAO = new HibernateTasksDAO();
    public static SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public static List tasks = new ArrayList<Tasks>();

    public static void createAndSaveNewTask(String deadline, String description) throws Exception {
        Date dateDeadline = sp.parse(deadline);
        Tasks newTasks = new Tasks(description,dateDeadline);
        hibernateTasksDAO.save(newTasks);
    }
    public static void deleteTask(String idForDelete)throws Exception{
        int idTask = Integer.parseInt(idForDelete);
        hibernateTasksDAO.deleteTasks(hibernateTasksDAO.getTasksById(idTask));
    }
    public static List getAllTasks() throws Exception {
        tasks = hibernateTasksDAO.getAllTasks();
        return tasks;
    }

}