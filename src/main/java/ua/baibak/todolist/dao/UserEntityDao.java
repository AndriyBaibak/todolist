package ua.baibak.todolist.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import ua.baibak.todolist.entity.User;

import javax.inject.Inject;
import java.util.List;

public class UserEntityDao {

    @Inject
    private SessionFactory sessionFactory;
    @Autowired
    private Md5PasswordEncoder passwordEncoder;


    public void createNewUser(User newUser) throws HibernateException {
        User temporaryUser = new User(newUser);
        temporaryUser.setPassword(passwordEncoder.encodePassword(temporaryUser.getPassword(), "123"));
        temporaryUser.setConfirmPassword(passwordEncoder.encodePassword(temporaryUser.getConfirmPassword(), "123"));
        sessionFactory.getCurrentSession().save(temporaryUser);
    }

    public User findUserByName(String userName) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("userName", userName));
        return (User) criteria.uniqueResult();
    }

    public boolean checkUniqueUserName(String userName) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        List<User> usersNames = session.createCriteria(User.class).add(Restrictions.eq("userName", userName)).list();
        if (usersNames.size() > 0) {
            return true;
        }
        return false;
    }
}