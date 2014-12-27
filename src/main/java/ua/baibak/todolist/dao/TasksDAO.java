package ua.baibak.todolist.dao;

import ua.baibak.todolist.entity.Tasks;

import java.util.List;

/**
 * Created by Андрей on 23.11.2014.
 */
public interface TasksDAO {
    public void save (Tasks todo) throws Exception;
    public void updateTasks(Tasks todo) throws Exception;
    public Tasks getTasksById(int id) throws Exception;
    public List getAllTasks() throws Exception;
    public void deleteTasks(Tasks todo) throws Exception;
}
