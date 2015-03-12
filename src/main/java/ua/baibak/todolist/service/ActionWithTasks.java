package ua.baibak.todolist.service;

import java.util.Date;
import java.util.List;

public interface ActionWithTasks {
    public void createAndSaveNewTask(String description, Date deadline) throws Exception;

    public void deleteTask(String idForDelete) throws Exception;

    public List getAllTasks() throws Exception;

    public void updateTasks(String newDescription, String id, String type) throws Exception;
}