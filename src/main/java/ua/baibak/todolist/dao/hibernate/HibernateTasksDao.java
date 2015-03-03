package ua.baibak.todolist.dao.hibernate;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.dao.TasksDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HibernateTasksDao implements TasksDao {

    private static Logger log = Logger.getLogger(HibernateTasksDao.class);
    private SessionFactory mySessionFactory;

    public HibernateTasksDao(SessionFactory sf){
        this.mySessionFactory = sf;
    }

    @Override
    public void save(String description, Date deadline) throws Exception {
        Session session = null;
        Tasks tasks = new Tasks(description, deadline);
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(tasks);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during saving task", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        Session session = null;
        Tasks taskForUpdate = this.getTasksById(Integer.parseInt(id));
        if (type.equals("newDescription")) {
            taskForUpdate.setDescription(newData);
        } else {
            taskForUpdate.setDeadline((Date) java.sql.Date.valueOf(newData));
        }
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.update(taskForUpdate);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during updating task ", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Tasks getTasksById(int id) throws Exception {
        Session session = null;
        Tasks res = null;
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            res = (Tasks) session.get(ua.baibak.todolist.entity.Tasks.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during getTasksById", e);
            session.getTransaction().rollback();
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
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            tasks = session.createCriteria(ua.baibak.todolist.entity.Tasks.class).addOrder(org.hibernate.criterion.Order.asc("deadline")).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during  getAllTasks ", e);
            session.getTransaction().rollback();
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
        Tasks taskForDelete = this.getTasksById(id);
        try {
            session = mySessionFactory.getCurrentSession();
            session.beginTransaction();
            session.delete(taskForDelete);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception during delete task", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}