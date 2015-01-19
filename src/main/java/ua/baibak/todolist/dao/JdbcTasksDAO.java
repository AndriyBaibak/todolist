package ua.baibak.todolist.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import ua.baibak.todolist.interfaces.TasksDAO;
import ua.baibak.todolist.entity.Tasks;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class JDBCTasksDAO implements TasksDAO {

    private Connection dbConnection = null;
    private Statement statement = null;
    private AtomicInteger counter = new AtomicInteger();
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(JDBCTasksDAO.class);
    private Session session = null;

    @Override
    public java.sql.Connection getConnection() throws Exception {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            dbConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/todolist","root", "root");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return dbConnection;
    }


    @Override
    public void save(String description, Date deadline)throws Exception{
        String insertTableSQL = "INSERT INTO tasks VALUES ('" + counter.toString() + "','" +  description + "','" + changeUtilDateToSQL(new Date())+ "','" + changeUtilDateToSQL(deadline) +"');";
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(insertTableSQL);
            counter.incrementAndGet() ;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
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
    public void updateTasks(Tasks todo) throws Exception {

    }

    @Override
    public List getAllTasks() throws Exception {
        List tasks = new ArrayList<Tasks>();
        String selectTableSQL = "SELECT*FROM tasks ORDER BY deadline  ";
        try {
            dbConnection = getConnection();
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
            dbConnection = getConnection();
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