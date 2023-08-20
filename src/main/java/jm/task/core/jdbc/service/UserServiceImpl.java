package jm.task.core.jdbc.service;

import com.mysql.cj.xdevapi.SessionFactory;
import jm.task.core.jdbc.dao.UserDaoHibernateImpl;
import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.cfg.Configuration;


import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl implements UserService {

    //UserDaoJDBCImpl service;
    UserDaoHibernateImpl service;

    public UserServiceImpl() {
        service = new UserDaoHibernateImpl();
    }

    public void createUsersTable() {
        service.createUsersTable();
    }

    public void dropUsersTable() {
        service.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        service.saveUser(name, lastName, age);
    }

    public void removeUserById(long id) {
        service.removeUserById(id);
    }

    public List<User> getAllUsers() {
        return service.getAllUsers();
    }

    public void cleanUsersTable() {
        service.cleanUsersTable();
    }
}
