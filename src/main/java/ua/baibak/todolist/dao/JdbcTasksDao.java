package ua.baibak.todolist.dao;

import org.apache.log4j.Logger;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.TasksDao;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
    private AtomicInteger counter = new AtomicInteger(this.selectLastId());
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(JdbcTasksDao.class);
    private static InitialContext ic = null;
    private static DataSource ds = null;



   static{
        try {
            ic = new InitialContext();
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist");
        } catch (Exception e) {
           log.error("Exception in InitialContext" + e);
           System.exit(434);
        }

    }

    
    @Override
    public void save(String description, Date deadline)throws Exception{
        Connection dbConnection = null;
        Statement statement = null;
        String insertTableSQL = "INSERT INTO tasks VALUES ('" + counter.incrementAndGet() + "','" +  description + "','" + changeUtilDateToSQL(new Date())+ "','" + changeUtilDateToSQL(deadline) +"');";
        try {
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(insertTableSQL);
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
       Connection dbConnection = null;
       Statement statement = null;
       String updateDescriptionSQL = "UPDATE tasks SET description = '" + newData + "' WHERE id=" + id + ";";
       String updateDateSQL = "UPDATE tasks SET deadline = '" + newData + "' WHERE id = " + id + ";";

        try {
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
        Connection dbConnection = null;
        Statement statement = null;
        List tasks = new ArrayList<Tasks>();
        String selectTableSQL = "SELECT*FROM tasks ORDER BY deadline;  ";
        try {
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
        Connection dbConnection = null;
        Statement statement = null;
        String deleteTableSQL = "DELETE FROM tasks WHERE id = " + id + ";";
        try {
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

    public int selectLastId(){
        int lastId = 1;
        Connection dbconnection = null;
        Statement statement = null;
        String selectLastIdSQL = "SELECT id FROM tasks;";
        try {
            dbconnection=ds.getConnection();
            statement=dbconnection.createStatement();
            ResultSet rs = statement.executeQuery(selectLastIdSQL);
            while(rs.next()){
                lastId = rs.getInt("id");
            }
        } catch (SQLException e) {
           log.error("Problem in selectLastId-method");
        }
        System.out.println("in selectLastIs" + lastId);
        return lastId;
    }


}