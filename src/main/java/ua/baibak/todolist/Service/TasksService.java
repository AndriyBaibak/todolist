package ua.baibak.todolist.service;

import ua.baibak.todolist.dao.JDBCTasksDAO;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.ActionWithTasks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 16.12.2014.
 */
public class TasksService implements ActionWithTasks {

    public  JDBCTasksDAO jdbcTasksDAO = new JDBCTasksDAO();
    public  SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public  List tasks = new ArrayList<Tasks>();

    @Override
    public  void createAndSaveNewTask(String deadline, String description) throws Exception {
        if(description == ""){
            throw new Exception("Опис завдання відсутній.");
        }
        java.util.Date dateDeadline = sp.parse(deadline);
        jdbcTasksDAO.save(description,dateDeadline);
    }
    @Override
    public  void deleteTask(String idForDelete)throws Exception{
        int idTask = Integer.parseInt(idForDelete);
        jdbcTasksDAO.deleteTasks(idTask);
    }
    @Override
    public  List getAllTasks() throws Exception {
        tasks = jdbcTasksDAO.getAllTasks();
        return tasks;
    }


}