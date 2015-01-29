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
    private Connection dbConnection;
    private Statement statement = null;
    private AtomicInteger counter = new AtomicInteger();
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(JdbcTasksDao.class);


    private InitialContext ic;
    private DataSource ds;






    @Override
    public void save(String description, Date deadline)throws Exception{
        String insertTableSQL = "INSERT INTO tasks VALUES ('" + counter.toString() + "','" +  description + "','" + changeUtilDateToSQL(new Date())+ "','" + changeUtilDateToSQL(deadline) +"');";
        try {
            ic = new InitialContext();
            System.out.println("per lookup in save");
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist"); // вместо написать java:/comp/env/jdbc/TestDB
            System.out.println(ds.toString()+ " in getConnection");
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(insertTableSQL);
            counter.incrementAndGet() ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.debug(e);
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
    public void updateTasks(String newDesciption, String id) throws Exception {
       String updateTableSQL = "UPDATE tasks SET description = '" + newDesciption + "' WHERE id=" + id + ";";
        try {
            ic = new InitialContext();
            System.out.println("per lookup in update");
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist"); // вместо написать java:/comp/env/jdbc/TestDB
            System.out.println(ds.toString()+ " in getConnection");
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(updateTableSQL);
            System.out.println("Record is update from tasks table!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.debug("error in update catch " + e);
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
        log.debug("in get all tasks method");
        try {
            ic = new InitialContext();
            System.out.println("per lookup in getalltasks");
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist"); // вместо написать java:/comp/env/jdbc/TestDB
            System.out.println(ds.toString()+ " in getConnection");
            dbConnection = ds.getConnection();
            log.debug("in get all tasks method");
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
            System.out.println(e.getMessage());
        }
        return tasks;
    }


    @Override
    public void deleteTasks(int id) throws Exception {
        String deleteTableSQL = "DELETE FROM tasks WHERE id = " + id +";";
        try {
            ic = new InitialContext();
            System.out.println("per lookup in delete");
            ds = (DataSource) ic.lookup("java:/comp/env/jdbc/todolist"); // вместо написать java:/comp/env/jdbc/TestDB
            System.out.println(ds.toString()+ " in getConnection");
            dbConnection = ds.getConnection();
            statement = dbConnection.createStatement();
            statement.execute(deleteTableSQL);
            System.out.println("Record is deleted from tasks table!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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