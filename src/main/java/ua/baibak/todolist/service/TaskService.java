package ua.baibak.todolist.service;


import ua.baibak.todolist.dao.TaskDao;
import ua.baibak.todolist.entity.Task;

import java.util.List;


public class TaskService implements ActionWithTask {


    private TaskDao taskDao;

    public TaskService(TaskDao taskDao){
        this.taskDao = taskDao;
    }

    @Override
    public void createAndSaveNewTask(Task taskForSave) throws Exception {
        taskDao.save(taskForSave);
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