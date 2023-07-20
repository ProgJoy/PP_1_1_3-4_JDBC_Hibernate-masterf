package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    private static final String MYURL = "jdbc:mysql://localhost:3306/kata";
    private static final String NAME = "root";
    private static final String PASS = "1234";
    Connection connection = null;
    public Connection getConnection () {
        try {
            connection = DriverManager.getConnection(MYURL, NAME, PASS);
            Statement statement = connection.createStatement();
            statement.execute("insert into user (name, lastName, age) value ('Artem', 'Polmoliv', 21);\n");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
}
