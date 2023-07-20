package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Util {
    private static final String MYURL = "jdbc:mysql://localhost:3306/kata";
    private static final String NAME = "root";
    private static final String PASS = "1234";
    private Connection connection = null;

    public Connection getConnection () {
        try {
            connection = DriverManager.getConnection(MYURL, NAME, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public SessionFactory getFactory() {
        Configuration configuration = new Configuration();
        Properties properties = new Properties();
        properties.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
        properties.put(Environment.URL, MYURL);
        properties.put(Environment.USER, NAME);
        properties.put(Environment.PASS, PASS);
        properties.put(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
        properties.put(Environment.SHOW_SQL, "true");
        properties.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
        configuration.setProperties(properties);

        configuration.addAnnotatedClass(User.class);

        SessionFactory sessionFactory = configuration
                .addAnnotatedClass(User.class)
                .addProperties(configuration.getProperties())
                .buildSessionFactory();

        return sessionFactory;

    }
}
