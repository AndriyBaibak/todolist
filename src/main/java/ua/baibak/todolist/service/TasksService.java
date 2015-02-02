package ua.baibak.todolist.service;

import ua.baibak.todolist.dao.JdbcTasksDao;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.ActionWithTasks;
import ua.baibak.todolist.interfaces.TasksDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 16.12.2014.
 */
public class TasksService implements ActionWithTasks {

    public TasksDao jdbcTasksDAO = new JdbcTasksDao();
    public  SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public  List tasks = new ArrayList<Tasks>();
    public static TasksService objectToActionTasks = new TasksService();

    public static TasksService getObjectToActionTasks() {
        return objectToActionTasks;
    }

    public static void setObjectToActionTasks(TasksService objectToActionTasks) {
        TasksService.objectToActionTasks = objectToActionTasks;
    }

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

    @Override
    public void updateTasks(String newData, String id, String type)throws Exception{
        jdbcTasksDAO.updateTasks(newData,id,type);
    }
}