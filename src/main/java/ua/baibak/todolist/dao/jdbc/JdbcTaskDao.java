package ua.baibak.todolist.dao.jdbc;

import org.apache.log4j.Logger;
import ua.baibak.todolist.dao.TaskDao;
import ua.baibak.todolist.entity.Task;
import ua.baibak.todolist.utils.DateUtil;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JdbcTaskDao implements TaskDao {

    private static Logger log = Logger.getLogger(JdbcTaskDao.class);
    private AtomicInteger counter = new AtomicInteger();
    private DataSource ds = null;

    public JdbcTaskDao(DataSource dataSource) {
        this.ds = dataSource;
        try {
            counter.set(this.selectLastId());
        } catch (SQLException e) {
             log.error("Exception" + e);
        }
    }

    @Override
    public void saveNewTask(Task taskForSave) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String saveTask = "INSERT INTO tasks VALUES ('" + counter.incrementAndGet() + "','" + taskForSave.getDescription() + "','" + DateUtil.changeUtilDateToSqlDate(new Date()) + "','" + DateUtil.changeUtilDateToSqlDate(taskForSave.getDeadline())  + "','" + taskForSave.getAuthor() + "');";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(saveTask);
        } catch (SQLException e) {
            log.error("SQLException during saving" + e);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    @Override
    public void updateTask(Task taskForUpdate, String id) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        String updateDescriptionTask = "UPDATE tasks SET description = '" + taskForUpdate.getDescription() + "' WHERE id=" + id + ";";
        String updateDateTask = "UPDATE tasks SET deadline = '" + taskForUpdate.getDeadline() + "' WHERE id = " + id + ";";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(updateDescriptionTask);
            statement.executeUpdate(updateDateTask);
        } catch (SQLException e) {
            log.error("SQLException during updating " + e);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    @Override
    public List getAllTasks(String author) throws SQLException {
        Connection dbConnection = null;
        Statement statement = null;
        List tasks = new ArrayList<Task>();
        String getAllTask = "SELECT tasks.id,tasks.description,tasks.createdDate,tasks.deadline, tasks.author FROM tasks WHERE author ='" + author + "'ORDER BY deadline;";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(getAllTask);
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                Date createdDate = rs.getDate("createdDate");
                Date deadline = rs.getDate("deadline");
                String taskAuthor = rs.getString("author");
                Task taskForView = new Task(id, description, createdDate, deadline, taskAuthor);
                tasks.add(taskForView);
            }
        } catch (SQLException e) {
            log.error("SQLException during getAllTasks " + e);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
        return tasks;
    }

    @Override
    public void deleteTask(int id) throws Exception {
        Connection dbConnection = null;
        Statement statement = null;
        String deleteTask = "DELETE FROM tasks WHERE id = " + id + ";";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.execute(deleteTask);
        } catch (SQLException e) {
            log.error("SQLException during deletin task " + e);
            throw e;
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public int selectLastId()throws SQLException{
        int lastId = 1;
        Connection dbconnection = null;
        Statement statement = null;
        String selectLastIdSQL = "SELECT id FROM tasks;";
        try {
            dbconnection = ds.getConnection();
            statement = dbconnection.createStatement();
            ResultSet rs = statement.executeQuery(selectLastIdSQL);
            while (rs.next()) {
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
            log.error("Problem in selectLastId");
            throw e;
        }
        return lastId;
    }
}