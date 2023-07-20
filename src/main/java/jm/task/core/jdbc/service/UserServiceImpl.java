package jm.task.core.jdbc.service;

import jm.task.core.jdbc.dao.UserDaoJDBCImpl;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.SQLException;
import java.util.List;

public class UserServiceImpl extends Util implements UserService {
    UserDaoJDBCImpl service;

    public UserServiceImpl() {
        service = new UserDaoJDBCImpl();
    }

    public void createUsersTable() {
        service.createUsersTable();
    }

    public void dropUsersTable() {
        service.dropUsersTable();
    }

    public void saveUser(String name, String lastName, byte age) {
        try {
            service.saveUser(name, lastName, age);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        try {
            service.removeUserById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<User> getAllUsers() {
        try {
            return service.getAllUsers();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void cleanUsersTable() {
        service.cleanUsersTable();
    }
}
