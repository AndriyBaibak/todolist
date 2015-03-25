package ua.baibak.todolist.dao;

import ua.baibak.todolist.entity.Task;

import java.util.Date;
import java.util.List;

public interface TaskDao {
    public void save(Task taskForSave) throws Exception;

    public void updateTasks(Task taskForUpdate, String id) throws Exception;

    public List getAllTasks() throws Exception;

    public void deleteTasks(int id) throws Exception;

}
