package ua.baibak.todolist.service.user;

import org.hibernate.HibernateException;
import org.springframework.transaction.annotation.Transactional;
import ua.baibak.todolist.dao.UserEntityDao;
import ua.baibak.todolist.entity.User;

import javax.inject.Inject;

@Transactional
public class TransactionUserService implements UserService {

    @Inject
    private UserEntityDao userEntityDao;

    @Override
    public void createNewUser(User newUser) throws HibernateException {
       userEntityDao.createNewUser(newUser);
    }

    public User findUserByName(String userName) throws Exception {
        return userEntityDao.findUserByName(userName);
    }

    public boolean checkUniqueUserName(String userName) throws Exception {
        return userEntityDao.checkUniqueUserName(userName);
    }
}