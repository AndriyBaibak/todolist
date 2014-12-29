package ua.baibak.todolist.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import ua.baibak.todolist.entity.Tasks;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Андрей on 23.11.2014.
 */
public class JdbcTasksDAO implements TasksDAO {

    private Connection dbConnection = null;
    private Statement statement = null;
    private int count = 1;
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(JdbcTasksDAO.class);
    private Session session;

    @Override
    public java.sql.Connection getConnection() throws Exception {
       java.sql.Connection dbConnection = null;
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
    public void createTable() throws Exception {

        String createTableSQL = "CREATE TABLE tasks("
                + "id INT(10) NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                + "description VARCHAR(200) NOT NULL, "
                + "createdDate DATE NOT NULL, "
                + "deadline DATE NOT NULL"
                + ")";
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            statement.execute(createTableSQL);
            System.out.println("Table \"tasks\" is created!");
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
    public void save(String description, Date deadline)throws Exception{
        Connection dbConnection = null;
        Statement statement = null;
        System.out.println(description);
        String insertTableSQL = "INSERT INTO tasks VALUES ('" + count + "','" +  description + "','" +  new java.sql.Date(System.currentTimeMillis())+ "','" + deadline +"');";
        System.out.println(insertTableSQL);
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();
            statement.executeUpdate(insertTableSQL);
            count++ ;
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
    public Tasks getTasksById(int id) throws Exception {
        Tasks res = null;

        return res;
    }

    @Override
    public List getAllTasks() throws Exception {
        List tasks = new ArrayList<Tasks>();
        String selectTableSQL = "SELECT*FROM tasks";
        try {
            dbConnection = getConnection();
            statement = dbConnection.createStatement();

            // выбираем данные с БД
            ResultSet rs = statement.executeQuery(selectTableSQL);

            // И если что то было получено то цикл while сработает
            while (rs.next()) {
                int id = rs.getInt("id");
                String description = rs.getString("description");
                java.sql.Date createdDate = rs.getDate("createdDate");
                java.sql.Date deadline = rs.getDate("deadline");
                System.out.println("id : " + id);
                System.out.println("description : " + description);
                System.out.println("createdDate : " + createdDate);
                System.out.println("deadline : " + deadline);
                Tasks taskforview = new Tasks(id, description, createdDate, deadline);
                tasks.add(taskforview);
                System.out.println(taskforview.toString());
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
            // выполняем запрос delete SQL
            statement.execute(deleteTableSQL);
            System.out.println("Record is deleted from tasks table!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}