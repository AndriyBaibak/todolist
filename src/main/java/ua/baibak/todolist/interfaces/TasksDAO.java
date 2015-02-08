package ua.baibak.todolist.interfaces;

import ua.baibak.todolist.entity.Tasks;

import java.util.Date;
import java.util.List;

/**
 * Created by Андрей on 23.11.2014.
 */
public interface TasksDAO {
    public void save (String description, Date deadline) throws Exception;
    public void updateTasks(String newData, String id, String type) throws Exception;
    public List getAllTasks() throws Exception;
    public void deleteTasks(int id) throws Exception;
}
