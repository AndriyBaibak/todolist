package ua.baibak.todolist.service;


import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.ActionWithTasks;
import ua.baibak.todolist.interfaces.TasksDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TasksService implements ActionWithTasks {

    private TasksDao tasksDao = null;
    public SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
    public List tasks = new ArrayList<Tasks>();

    public void setTasksDao(TasksDao tasksDao) {
        this.tasksDao = tasksDao;
    }

    @Override
    public void createAndSaveNewTask(String description, String deadline) throws Exception {
        java.util.Date dateDeadline = sp.parse(deadline);
        tasksDao.save(description, dateDeadline);
    }

    @Override
    public void deleteTask(String idForDelete) throws Exception {
        int idTask = Integer.parseInt(idForDelete);
        tasksDao.deleteTasks(idTask);
    }

    @Override
    public List getAllTasks() throws Exception {
        tasks = tasksDao.getAllTasks();
        return tasks;
    }

    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        tasksDao.updateTasks(newData, id, type);
    }
}