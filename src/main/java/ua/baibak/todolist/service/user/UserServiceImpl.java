package ua.baibak.todolist.service.user;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ua.baibak.todolist.entity.User;

import javax.inject.Inject;

public class UserServiceImpl implements UserService {
    private static Logger log = Logger.getLogger(UserServiceImpl.class);
    @Inject
    private SessionFactory sessionFactory;


    @Override
    public void createNewUser(User newUser) throws Exception {
        Session session = null;
        try {
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(newUser);
            session.getTransaction().commit();
        } catch (Exception e) {
            log.error("exception during register new User" + e);
            session.getTransaction().rollback();
            throw e;
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}