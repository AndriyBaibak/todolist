package ua.baibak.todolist.dao;

import ua.baibak.todolist.entity.Task;

import java.util.List;

public interface TaskDao {
    public void save(Task taskForSave, String author) throws Exception;

    public void updateTask(Task taskForUpdate, String id, String author) throws Exception;

    public List getAllTasks(String author) throws Exception;

    public void deleteTask(int id , String author) throws Exception;

}
