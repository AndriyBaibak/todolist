package ua.baibak.todolist.Service;

import ua.baibak.todolist.dao.HibernateTasksDAO;
import ua.baibak.todolist.entity.Tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 16.12.2014.
 */
public class TasksService {

    public HibernateTasksDAO hibernateTasksDAO = new HibernateTasksDAO();
    public  SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public  List tasks = new ArrayList<Tasks>();

    public  void createAndSaveNewTask(String deadline, String description) throws Exception {
        if(description == ""){
            throw new Exception("Опис завдання відсутній.");
        }
        java.sql.Date dateDeadline = java.sql.Date.valueOf(deadline);
        Tasks newTasks = new Tasks(description, dateDeadline);

        hibernateTasksDAO.save(newTasks);
    }
    public  void deleteTask(String idForDelete)throws Exception{
        int idTask = Integer.parseInt(idForDelete);
        hibernateTasksDAO.deleteTasks(hibernateTasksDAO.getTasksById(idTask));
    }
    public  List getAllTasks() throws Exception {
        tasks = hibernateTasksDAO.getAllTasks();
        return tasks;
    }


}