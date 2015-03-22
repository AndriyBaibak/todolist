package ua.baibak.todolist.service;

import ua.baibak.todolist.entity.Task;

import java.util.Date;
import java.util.List;

public interface ActionWithTask {
    public void createAndSaveNewTask(String description, Date deadline) throws Exception;

    public void deleteTask(String idForDelete) throws Exception;

    public List getAllTasks() throws Exception;

    public void updateTasks(Task taskForUpdate, String id) throws Exception;
}