package ua.baibak.todolist.dao.hibernate;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import ua.baibak.todolist.dao.TaskDao;
import ua.baibak.todolist.entity.Task;

import java.util.List;

public class HibernateTaskDao implements TaskDao {

    private SessionFactory mySessionFactory;

    public HibernateTaskDao(SessionFactory sf) {
        this.mySessionFactory = sf;
    }

    @Override
    public void saveNewTask(Task taskForSave) throws Exception {
        Session session = mySessionFactory.getCurrentSession();
        session.save(taskForSave);
    }

    @Override
    public void updateTask(Task taskForUpdate, String id) throws Exception {
        Task taskToEdit = getTasksById(Integer.parseInt(id));
        taskToEdit.setDescription(taskForUpdate.getDescription());
        taskToEdit.setDeadline(taskForUpdate.getDeadline());
        Session session = mySessionFactory.getCurrentSession();
        session.update(taskToEdit);
    }

    public Task getTasksById(int id) throws HibernateException {
        Session session = mySessionFactory.getCurrentSession();
        return (Task) session.get(Task.class, id);
    }

    @Override
    public List getAllTasks(String author) throws Exception {
        Session session = mySessionFactory.getCurrentSession();
        List tasks = session.createCriteria(Task.class).add(Restrictions.eq("author", author)).addOrder(Order.asc("deadline")).list();
        return tasks;
    }

    @Override
    public void deleteTask(int id) throws HibernateException {
        Session session = mySessionFactory.getCurrentSession();
        Task taskForDelete = getTasksById(id);
        session.delete(taskForDelete);
    }
}