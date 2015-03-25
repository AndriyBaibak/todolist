package ua.baibak.todolist.dao.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.baibak.todolist.entity.Task;
import ua.baibak.todolist.dao.TaskDao;

import java.util.ArrayList;
import java.util.List;

public class HibernateTaskDao implements TaskDao {

    private static Logger log = Logger.getLogger(HibernateTaskDao.class);
    private SessionFactory mySessionFactory;

    public HibernateTaskDao(SessionFactory sf){
        this.mySessionFactory = sf;
    }

    @Override
    public void save(Task taskForSave) throws Exception {
        Session session = null;
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(taskForSave);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during saving task", e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTasks(Task taskForUpdate, String id) throws Exception {
        Session session = null;
        Task temporaryTask = this.getTasksById(Integer.parseInt(id));
        temporaryTask.setDescription(taskForUpdate.getDescription());
        temporaryTask.setDeadline(taskForUpdate.getDeadline());
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(temporaryTask);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during updating task ", e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Task getTasksById(int id) throws Exception {
        Session session = null;
        Task res = null;
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            res = (Task) session.get(Task.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
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
    public List getAllTasks() throws Exception {
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
    public void deleteTasks(int id) throws Exception {
        Session session = null;
        Task taskForDelete = this.getTasksById(id);
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(taskForDelete);
            session.getTransaction().commit();
        } catch (Exception e) {
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