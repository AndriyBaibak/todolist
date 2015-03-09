package ua.baibak.todolist.service;


import ua.baibak.todolist.dao.TasksDao;

import java.util.List;


public class TasksService implements ActionWithTasks {

    private TasksDao tasksDao;

    public TasksService(TasksDao task){
        this.tasksDao = task;
    }

    @Override
    public void createAndSaveNewTask(String description, String deadline) throws Exception {
        java.sql.Date dateDeadline = java.sql.Date.valueOf(deadline);
        tasksDao.save(description, DateUtil.changeSqlDateToUtilDate(dateDeadline));
    }

    @Override
    public void deleteTask(String idForDelete) throws Exception {
        int idTask = Integer.parseInt(idForDelete);
        tasksDao.deleteTasks(idTask);
    }

    @Override
    public List getAllTasks() throws Exception {
        return tasksDao.getAllTasks();
    }

    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        tasksDao.updateTasks(newData, id, type);
    }

}