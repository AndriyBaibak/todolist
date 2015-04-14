package ua.baibak.todolist.service.user;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ua.baibak.todolist.entity.User;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.List;

public class UserServiceImpl implements UserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class);
    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void createNewUser(User newUser) throws HibernateException {
        Session session = null;
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
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
    public String checkUserName(String userName) {
        Session session = null;
        List<String> usersNames = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            usersNames = session.createSQLQuery("SELECT userName FROM users").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            log.error("Exception during checkUserName" + e.toString());
            session.getTransaction().rollback();
        }
        if(usersNames.contains(userName)){
            return "yes";
        }
        return "no";
    }
}