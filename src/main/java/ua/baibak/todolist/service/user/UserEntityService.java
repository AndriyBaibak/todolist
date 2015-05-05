package ua.baibak.todolist.service.user;


import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ua.baibak.todolist.entity.User;

import javax.inject.Inject;
import java.util.List;

@Transactional
public class UserEntityService implements UserService {

    @Inject
    private SessionFactory sessionFactory;
    @Autowired
    private Md5PasswordEncoder passwordEncoder;

    @Override
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

    public boolean checkNameToBusy(String userName) throws Exception {
        Session session = sessionFactory.getCurrentSession();
        List<String> usersNames = session.createSQLQuery("SELECT userName FROM users").list();
        if (usersNames.contains(userName)) {
            return true;
        }
        return false;
    }
}