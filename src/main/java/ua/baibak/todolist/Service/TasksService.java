package ua.baibak.todolist.service;

import org.apache.log4j.Logger;
import ua.baibak.todolist.dao.HibernateTasksDao;
import ua.baibak.todolist.interfaces.ActionWithTasks;
import ua.baibak.todolist.interfaces.TasksDao;

import java.util.List;


public class TasksService implements ActionWithTasks {

    private TasksDao hibernateTasksDao = new HibernateTasksDao();
    private static Logger log = Logger.getLogger(TasksService.class);
    private static TasksService objectToActionTasks = new TasksService();

    public static TasksService getObjectToActionTasks() {
        return objectToActionTasks;
    }

    @Override
    public void createAndSaveNewTask(String description, String deadline) throws Exception {
        java.sql.Date dateDeadline = java.sql.Date.valueOf(deadline);
        hibernateTasksDao.save(description, DateService.changeSqlDateToUtilDate(dateDeadline));
    }

    @Override
    public void deleteTask(String idForDelete) throws Exception {
        int idTask = Integer.parseInt(idForDelete);
        hibernateTasksDao.deleteTasks(idTask);
    }

    @Override
    public List getAllTasks() throws Exception {
        List tasks = hibernateTasksDao.getAllTasks();
        return tasks;
    }

    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        hibernateTasksDao.updateTasks(newData, id, type);
    }

}