package dao;

import entity.Tasks;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Андрей on 23.11.2014.
 */
public interface TasksDAO {
    public void save (Tasks todo) throws SQLException;
    public void updateTasks(Tasks todo) throws SQLException;


    public Tasks getTasksById(int id) throws SQLException;
    public List getAllTasks() throws SQLException;
    public void deleteTasks(Tasks todo) throws SQLException;
}
