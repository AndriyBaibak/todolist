package ua.baibak.todolist.service.task;

import ua.baibak.todolist.entity.Task;

import java.util.Date;
import java.util.List;

public interface ActionWithTask {
    public void createAndSaveNewTask(Task taskForSave) throws Exception;

    public void deleteTask(String idForDelete) throws Exception;

    public List getAllTasks(String author) throws Exception;

    public void updateTasks(Task taskForUpdate, String id) throws Exception;
}