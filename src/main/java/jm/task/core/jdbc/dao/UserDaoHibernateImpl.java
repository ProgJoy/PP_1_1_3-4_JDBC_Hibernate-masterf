package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() {

    }


    @Override
    public void createUsersTable() {
        Session session = getFactory().getCurrentSession();
        session.beginTransaction();

        session.createNativeQuery("create TABLE IF NOT EXISTS  user (id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(15),lastName VARCHAR(15),age TINYINT);").executeUpdate();

        session.getTransaction().commit();
    }

    @Override
    public void dropUsersTable() {
        try (Session session = getFactory().openSession()) {
            session.beginTransaction();
            session.createNativeQuery("DROP table if exists user").executeUpdate();
            session.getTransaction().commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = getFactory().getCurrentSession();
        session.beginTransaction();

        session.persist(new User(name, lastName, age));

        session.getTransaction().commit();
    }

    @Override
    public void removeUserById(long id) {
        Session session = getFactory().getCurrentSession();
        session.beginTransaction();

        User userToDelete = session.get(User.class, id);
        session.delete(userToDelete);

        session.getTransaction().commit();
        session.close();
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = getFactory().getCurrentSession()){
            session.beginTransaction();

            List<User> list = session.createQuery("from User", User.class).getResultList();

            session.getTransaction().commit();
            return list;
        }
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = getFactory().getCurrentSession()) {
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
        }
    }
}
