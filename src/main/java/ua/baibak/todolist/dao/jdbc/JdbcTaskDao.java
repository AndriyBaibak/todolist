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
        counter.set(this.selectLastId());
    }

    @Override
    public void save(Task taskForSave) throws Exception {
        Connection dbConnection = null;
        Statement statement = null;
        String insertTableSQL = "INSERT INTO tasks VALUES ('" + counter.incrementAndGet() + "','" + taskForSave.getDescription() + "','" + DateUtil.changeUtilDateToSqlDate(new Date()) + "','" + DateUtil.changeUtilDateToSqlDate(taskForSave.getDeadline()) + "');";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(insertTableSQL);
        } catch (SQLException e) {
            log.error("SQLException during saving" + e);
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
    public void updateTasks(Task taskForUpdate, String id) throws Exception {
        Connection dbConnection = null;
        Statement statement = null;
        String updateDescriptionSQL = "UPDATE tasks SET description = '" + taskForUpdate.getDescription() + "' WHERE id=" + id + ";";
        String updateDateSQL = "UPDATE tasks SET deadline = '" + taskForUpdate.getDeadline() + "' WHERE id = " + id + ";";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(updateDescriptionSQL);
            statement.executeUpdate(updateDateSQL);
        } catch (SQLException e) {
            log.error("SQLException during updating " + e);
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
    public List getAllTasks() throws Exception {
        Connection dbConnection = null;
        Statement statement = null;
        List tasks = new ArrayList<Task>();
        String selectTableSQL = "SELECT*FROM tasks ORDER BY deadline;  ";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                Date createdDate = rs.getDate("createdDate");
                Date deadline = rs.getDate("deadline");
                Task taskForView = new Task(id, description, createdDate, deadline);
                tasks.add(taskForView);
            }
        } catch (SQLException e) {
            log.error("SQLException during getAllTasks " + e);
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
    public void deleteTasks(int id) throws Exception {
        Connection dbConnection = null;
        Statement statement = null;
        String deleteTableSQL = "DELETE FROM tasks WHERE id = " + id + ";";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.execute(deleteTableSQL);
        } catch (SQLException e) {
            log.error("SQLException during deletin task " + e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public int selectLastId() {
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
        }
        return lastId;
    }
}