package ua.baibak.todolist.dao;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import ua.baibak.todolist.entity.Tasks;
import ua.baibak.todolist.interfaces.TasksDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HibernateTasksDao implements TasksDao {
    private Tasks tasks = null;
    private static Logger log = Logger.getLogger(HibernateTasksDao.class);
    private Session session;

    @Override
    public void save(String description, Date deadline) throws Exception {
        tasks = new Tasks(description, deadline);
        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(tasks);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception in save", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        Tasks taskForUpdate = this.getTasksById(Integer.parseInt(id));
        if (type.equals("newDescription")) {
            taskForUpdate.setDescription(newData);
        } else {
            taskForUpdate.setDeadline((Date) java.sql.Date.valueOf(newData));
        }
        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.update(taskForUpdate);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception in update ", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public Tasks getTasksById(int id) throws Exception {
        Tasks res = null;
        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            res = (Tasks) session.get(ua.baibak.todolist.entity.Tasks.class, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception in getTasksById", e);
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
        List tasks = new ArrayList<Tasks>();
        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            tasks = session.createCriteria(ua.baibak.todolist.entity.Tasks.class).addOrder(org.hibernate.criterion.Order.asc("deadline")).list();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception in  getAllTasks ", e);
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
        Tasks taskForDelete = this.getTasksById(id);
        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(taskForDelete);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("Exception in deleteTasks", e);
            session.getTransaction().rollback();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}