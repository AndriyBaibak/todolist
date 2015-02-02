package ua.baibak.todolist.interfaces;

import java.util.Date;
import java.util.List;

public interface TasksDao {

    public void save (String description, Date deadline) throws Exception;
    public void updateTasks(String newData, String id, String type) throws Exception;
    public List getAllTasks() throws Exception;
    public void deleteTasks(int id) throws Exception;
}
