package ua.baibak.todolist.service.user;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ua.baibak.todolist.entity.User;

import javax.inject.Inject;
import java.util.List;
@Transactional
public class UserEntityService implements UserService {

    private static Logger log = Logger.getLogger(UserEntityService.class);

    @Inject
    private SessionFactory sessionFactory;
    @Inject
    private Md5PasswordEncoder passwordEncoder;

    @Override
    public void createNewUser(User newUser) throws HibernateException {
        newUser.setPassword(passwordEncoder.encodePassword(newUser.getPassword(),null));
        newUser.setConfirmPassword(passwordEncoder.encodePassword(newUser.getConfirmPassword(),null));
        sessionFactory.getCurrentSession().save(newUser);
    }

    public User findUserByName(String userName) throws Exception {
        Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("userName", userName));
        return  (User) criteria.uniqueResult();

    }

    public boolean checkNameToBusy(String userName)throws Exception{
        Session session = sessionFactory.getCurrentSession();
        List<String> usersNames = session.createSQLQuery("SELECT userName FROM users").list();
        if (usersNames.contains(userName)) {
            return true;
        }
        return false;
    }
}