package ua.baibak.todolist.dao.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.baibak.todolist.dao.TaskDao;
import ua.baibak.todolist.entity.Task;

import java.util.ArrayList;
import java.util.List;

public class HibernateTaskDao implements TaskDao {

    private static Logger log = Logger.getLogger(HibernateTaskDao.class);
    private SessionFactory mySessionFactory;

    public HibernateTaskDao(SessionFactory sf) {
        this.mySessionFactory = sf;
    }

    @Override
    public void save(Task taskForSave, String author) throws Exception {
        Session session = null;
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(taskForSave);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Exception during saving task", e);
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTask(Task taskForUpdate, String id, String author) throws Exception {
        Session session = null;
        Task temporaryTask = this.getTasksById(Integer.parseInt(id),author);
        temporaryTask.setDescription(taskForUpdate.getDescription());
        temporaryTask.setDeadline(taskForUpdate.getDeadline());
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(temporaryTask);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Exception during updating task ", e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Task getTasksById(int id, String author) throws HibernateException {
        Session session = null;
        Task res = null;
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            res = (Task) session.get(Task.class, id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Exception during getTasksById", e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return res;
    }

    @Override
    public List getAllTasks(String author) throws Exception {
        Session session = null;
        List tasks = new ArrayList<Task>();
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            tasks = session.createCriteria(Task.class).addOrder(org.hibernate.criterion.Order.asc("deadline")).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during  getAllTasks ", e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tasks;
    }

    @Override
    public void deleteTask(int id, String author) throws HibernateException {
        Session session = null;
        Task taskForDelete = this.getTasksById(id, author);
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(taskForDelete);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Exception during delete task", e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}