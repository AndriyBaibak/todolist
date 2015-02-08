package ua.baibak.todolist.dao;


import ua.baibak.todolist.entity.Tasks;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import ua.baibak.todolist.interfaces.TasksDAO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Андрей on 23.11.2014.
 */
public class HibernateTasksDAO implements TasksDAO {
    private Tasks tasks = null;
    private static final long serialVersionUID = 1L;
    static Logger log = Logger.getLogger(HibernateTasksDAO.class);
    private Session session;

    @Override
    public void save(String description, Date deadline)throws Exception{
        tasks = new Tasks(description, deadline);
        log.debug(tasks.toString() + " ================================================");

        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.save(tasks);
            session.getTransaction().commit();
            log.debug("in save " + tasks);

        } catch (Exception e) {
            log.debug("Exception in method save from class HibernateTasksDAO", e);

        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
    @Override
    public void updateTasks(String newData, String id, String type) throws Exception {
        Tasks taskForUpdate = this.getTasksById(Integer.parseInt(id));

        if(type.equals("newDescription")){
            taskForUpdate.setDescription(newData);
        }else {
            taskForUpdate.setDeadline((Date)java.sql.Date.valueOf(newData));
        }
            try {
                session = HibernateUtill.getSessionFactory().getCurrentSession();
                session.beginTransaction();
                session.update(taskForUpdate);
                session.getTransaction().commit();

                System.out.println(tasks.toString());

            } catch (Exception e) {
                log.debug("Exception in method save from class HibernateTasksDAO", e);

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

        List tasks = new ArrayList<Tasks>();
        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            log.debug("in getall before res=   --------------------");
            session.beginTransaction();
            tasks = session.createCriteria(ua.baibak.todolist.entity.Tasks.class).addOrder(org.hibernate.criterion.Order.asc("deadline")).list();
            session.getTransaction().commit();
            log.debug("in getall after res=     --------------------");
        } catch (Exception e) {

            log.debug("Exception in method getAllTasks from class HibernateTasksDAO", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return tasks;
    }


    @Override
    public void deleteTasks(int id) throws Exception {
        try {
            session = HibernateUtill.getSessionFactory().getCurrentSession();
            session.beginTransaction();
            session.delete(this.getTasksById(id));
            session.getTransaction().commit();
            System.out.println((this.getTasksById(id)).toString());
        } catch (Exception e) {
            log.debug("Exception in method deleteTasks from class HibernateTasksDAO", e);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}