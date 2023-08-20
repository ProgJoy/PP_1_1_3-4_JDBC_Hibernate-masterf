package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Util util;
    Session session = null;

    public UserDaoHibernateImpl() {
        util = new Util();
    }


    @Override
    public void createUsersTable() {
        try {
            session = util.getFactory().getCurrentSession();
            session.beginTransaction();
            session.createNativeQuery("create TABLE IF NOT EXISTS  user (id INT PRIMARY KEY AUTO_INCREMENT,name VARCHAR(15),lastName VARCHAR(15),age TINYINT);").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.beginTransaction().rollback(); // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        try (Session session = util.getFactory().openSession()) {

            session.beginTransaction();
            session.createNativeQuery("DROP table if exists user").executeUpdate();
            session.getTransaction().commit();
        } catch (Exception e){
            if (session != null) {
                session.beginTransaction().rollback(); // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        try {
            session = util.getFactory().getCurrentSession();
            session.beginTransaction();

            session.persist(new User(name, lastName, age));

            session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.beginTransaction().rollback(); // Откатываем транзакцию в случае ошибки
        }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        try{
        Session session = util.getFactory().getCurrentSession();
        session.beginTransaction();

        User userToDelete = session.get(User.class, id);
        session.delete(userToDelete);

        session.getTransaction().commit();
        } catch (Exception e) {
            if (session != null) {
                session.beginTransaction().rollback(); // Откатываем транзакцию в случае ошибки
            }
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (Session session = util.getFactory().getCurrentSession()){
            session.beginTransaction();

            List<User> list = session.createQuery("from User", User.class).getResultList();

            session.getTransaction().commit();
            return list;

            } catch (Exception e) {
                if (session != null) {
                    session.beginTransaction().rollback(); // Откатываем транзакцию в случае ошибки
                }
                e.printStackTrace();
            }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try (Session session = util.getFactory().getCurrentSession()) {
            try{
            session.beginTransaction();
            session.createNativeQuery("TRUNCATE TABLE user").executeUpdate();
            session.getTransaction().commit();
            } catch (Exception e) {
                if (session != null) {
                    session.beginTransaction().rollback(); // Откатываем транзакцию в случае ошибки
                }
                e.printStackTrace();
            }
        }
    }
}
