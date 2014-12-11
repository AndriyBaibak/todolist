package dao;

import entity.Tasks;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 23.11.2014.
 */
public class TasksDAOImpl implements TasksDAO {
    private Session session;

    @Override
    public void save(Tasks todo) {
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(todo);
            session.getTransaction().commit();

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTasks(Tasks todo) throws SQLException {

    }

    @Override
    public Tasks getTasksById(int id) throws SQLException {
        Tasks res = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            res = (Tasks) session.get(entity.Tasks.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return res;
    }

    @Override
    public List getAllTasks() throws SQLException {
        Session session = null;
        List tasks = new ArrayList<Tasks>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tasks = session.createCriteria(entity.Tasks.class).addOrder(org.hibernate.criterion.Order.asc("deadline")).list();
        } catch (Exception e) {

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tasks;
    }


    @Override
    public void deleteTasks(Tasks todo) throws SQLException {
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(todo);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}