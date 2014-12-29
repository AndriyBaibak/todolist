package ua.baibak.todolist.Service;

import ua.baibak.todolist.dao.JdbcTasksDAO;
import ua.baibak.todolist.entity.Tasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 16.12.2014.
 */
public class TasksService {

    public JdbcTasksDAO jdbcTasksDAO = new JdbcTasksDAO();
    public  SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public  List tasks = new ArrayList<Tasks>();

    public  void createAndSaveNewTask(String deadline, String description) throws Exception {
        if(description == ""){
            throw new Exception("Опис завдання відсутній.");
        }
        java.sql.Date dateDeadline = java.sql.Date.valueOf(deadline);
        System.out.println(description);
        jdbcTasksDAO.save(description,dateDeadline);
    }
    public  void deleteTask(String idForDelete)throws Exception{
        int idTask = Integer.parseInt(idForDelete);
       jdbcTasksDAO.deleteTasks(idTask);
    }
    public  List getAllTasks() throws Exception {
        tasks = jdbcTasksDAO.getAllTasks();
        return tasks;
    }
    public void createTable(){
        try {
            jdbcTasksDAO.createTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}