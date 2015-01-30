package ua.baibak.todolist.dao;

import org.apache.log4j.Logger;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.TasksDao;

import javax.naming.InitialContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JdbcTasksDao implements TasksDao {
    private Connection dbConnection = null;
    private Statement statement = null;
    private AtomicInteger counter = new AtomicInteger(1);
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(JdbcTasksDao.class);
    private InitialContext ic = null;
    private DataSource ds = null;

    @Override
    public void save(String description, Date deadline)throws Exception{
        String insertTableSQL = "INSERT INTO tasks VALUES ('" + counter.toString() + "','" +  description + "','" + changeUtilDateToSQL(new Date())+ "','" + changeUtilDateToSQL(deadline) +"');";
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist");
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(insertTableSQL);
            counter.incrementAndGet() ;
        } catch (SQLException e) {
            log.debug("SQLException in save method" + e);
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
    public void updateTasks(String newData, String id, String type) throws Exception {

       String updateDescriptionSQL = "UPDATE tasks SET description = '" + newData + "' WHERE id=" + id + ";";
       String updateDateSQL = "UPDATE tasks SET deadline = '" + newData + "' WHERE id = " + id + ";";

        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist");
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            if(type.equals("newDescription")) {
                statement.executeUpdate(updateDescriptionSQL);
            }else {
                statement.executeUpdate(updateDateSQL);
            }
        } catch (SQLException e) {
            log.debug("SQLException in update method" + e);
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
        List tasks = new ArrayList<Tasks>();
        String selectTableSQL = "SELECT*FROM tasks ORDER BY deadline;  ";
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist");
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            ResultSet rs = statement.executeQuery(selectTableSQL);
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                java.util.Date createdDate = rs.getDate("createdDate");
                java.util.Date deadline = rs.getDate("deadline");
                Tasks taskforview = new Tasks(id, description, createdDate, deadline);
                tasks.add(taskforview);
            }
        } catch (SQLException e) {
            log.debug("SQLException in getalltasks method " + e);
        }finally {
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
        String deleteTableSQL = "DELETE FROM tasks WHERE id = " + id + ";";
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist");
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.execute(deleteTableSQL);
        } catch (SQLException e) {
            log.debug("SQLException in deleteTasks method " + e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (dbConnection != null) {
                dbConnection.close();
            }
        }
    }

    public static Date changeSQLDateToUtil(java.sql.Date sqldate) {
        java.util.Date utildate = new java.util.Date(sqldate.getTime());
        return utildate;
    }

    public static java.sql.Date changeUtilDateToSQL(java.util.Date utildate) {
        java.sql.Date sqldate=new java.sql.Date(utildate.getTime());
        return sqldate;
    }


}