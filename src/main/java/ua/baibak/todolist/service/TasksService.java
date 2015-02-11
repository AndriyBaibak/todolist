package ua.baibak.todolist.service;

import ua.baibak.todolist.dao.JdbcTasksDao;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.ActionWithTasks;
import ua.baibak.todolist.interfaces.TasksDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TasksService implements ActionWithTasks {

    public TasksDao jdbcTasksDao = new JdbcTasksDao();
    public SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public List tasks = new ArrayList<Tasks>();
    public static TasksService objectToActionTasks = new TasksService();

    public static TasksService getObjectToActionTasks() {
        return objectToActionTasks;
    }

    public static void setObjectToActionTasks(TasksService objectToActionTasks) {
        TasksService.objectToActionTasks = objectToActionTasks;
    }

    @Override
    public void createAndSaveNewTask(String description, String deadline) throws Exception {
        java.util.Date dateDeadline = sp.parse(deadline);
        jdbcTasksDao.save(description, dateDeadline);
    }

    @Override
    public void deleteTask(String idForDelete) throws Exception {
        int idTask = Integer.parseInt(idForDelete);
        jdbcTasksDao.deleteTasks(idTask);
    }

    @Override
    public List getAllTasks() throws Exception {
        tasks = jdbcTasksDao.getAllTasks();
        return tasks;
    }

    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        jdbcTasksDao.updateTasks(newData, id, type);
    }
}