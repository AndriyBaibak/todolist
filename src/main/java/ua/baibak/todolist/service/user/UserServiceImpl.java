package ua.baibak.todolist.service.user;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import ua.baibak.todolist.entity.User;

import javax.inject.Inject;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class);

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public void createNewUser(User newUser) throws HibernateException {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("exception during register new User" + e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public User findUserByName(String userName) throws Exception {
        Session session = null;
        User user = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("userName", userName));
            user = (User) criteria.uniqueResult();
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("exception during findUserByName" + e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return user;
    }

    public boolean validationUserName(String userName) {
        Session session = null;
        List<String> usersNames = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            usersNames = session.createSQLQuery("SELECT userName FROM users").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Exception during validationUserName" + e.toString());
            session.getTransaction().rollback();
        }
        if (usersNames.contains(userName)) {
            return true;
        }
        return false;
    }
}