package dao;

import entity.Tasks;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Андрей on 23.11.2014.
 */
public class HibernateTasksDAO implements TasksDAO {
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(HibernateTasksDAO.class);
    private Session session;

    @Override
    public void save(Tasks todo)throws Exception{
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(todo);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.info("Exception in method save from class HibernateTasksDAO", e);

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTasks(Tasks todo) throws Exception {

    }

    @Override
    public Tasks getTasksById(int id) throws Exception {
        Tasks res = null;
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            res = (Tasks) session.get(entity.Tasks.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.debug("Exception in method getTasksById from class HibernateTasksDAO", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return res;
    }

    @Override
    public List getAllTasks() throws Exception {
        Session session = null;
        List tasks = new ArrayList<Tasks>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            tasks = session.createCriteria(entity.Tasks.class).addOrder(org.hibernate.criterion.Order.asc("deadline")).list();
        } catch (Exception e) {

            log.info("Exception in method getAllTasks from class HibernateTasksDAO", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tasks;
    }


    @Override
    public void deleteTasks(Tasks todo) throws Exception {
        try {
            session = HibernateUtil.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(todo);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.info("Exception in method deleteTasks from class HibernateTasksDAO", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}