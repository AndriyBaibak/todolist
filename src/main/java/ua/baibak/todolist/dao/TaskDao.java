package ua.baibak.todolist.dao;

import ua.baibak.todolist.entity.Task;

import java.util.List;

public interface TaskDao {
    public void saveNewTask(Task taskForSave) throws Exception;

    public void updateTask(Task taskForUpdate, String id) throws Exception;

    public List getAllTasks(String author) throws Exception;

    public void deleteTask(int id ) throws Exception;

}
