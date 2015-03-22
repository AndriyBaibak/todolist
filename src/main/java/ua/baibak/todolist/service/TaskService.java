package ua.baibak.todolist.service;


import ua.baibak.todolist.dao.TaskDao;
import ua.baibak.todolist.entity.Task;

import java.util.Date;
import java.util.List;


public class TaskService implements ActionWithTask {

    private TaskDao taskDao;

    public TaskService(TaskDao task){
        this.taskDao = task;
    }

    @Override
    public void createAndSaveNewTask(String description, Date deadline) throws Exception {
        taskDao.save(description, deadline);
    }

    @Override
    public void deleteTask(String idForDelete) throws Exception {
        int idTask = Integer.parseInt(idForDelete);
        taskDao.deleteTasks(idTask);
    }

    @Override
    public List getAllTasks() throws Exception {
        return taskDao.getAllTasks();
    }

    @Override
    public void updateTasks(Task taskForUpdate, String id) throws Exception {
        taskDao.updateTasks(taskForUpdate, id);
    }

}