package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    Connection connection;
    private Util util = new Util();
    public UserDaoJDBCImpl() {
        connection = util.getConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS user ("
                + "id INT AUTO_INCREMENT PRIMARY KEY,"
                + "name VARCHAR(45) NOT NULL,"
                + "lastName VARCHAR(45) NOT NULL,"
                + "age INT"
                + ") CHARSET=utf8mb3";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.executeUpdate(sql);
            System.out.println("Таблица user успешно создана в базе данных.");

        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Ошибка при создании таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        String tableName = "user";
        String sql = "DROP TABLE IF EXISTS " + tableName;

        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) throws SQLException {
        PreparedStatement preparedStatement = null;
        String sqlAdd = "INSERT INTO USER (NAME, LASTNAME, AGE) VALUES (?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(sqlAdd);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setLong(3, age);

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        }
    }

    public void removeUserById(long id) throws SQLException {
        String sqlDelete = "DELETE FROM USER WHERE ID=?";
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(sqlDelete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }

    public List<User> getAllUsers() throws SQLException {
        List<User> users = new ArrayList<>();

        String sqlSelect = "SELECT * FROM USER";
        //PreparedStatement statement = null;
        try (PreparedStatement statement = connection.prepareStatement(sqlSelect);
             ResultSet resultSet = statement.executeQuery()){
            while (resultSet.next()) {
                User user = new User(resultSet.getString("name"),
                        resultSet.getString("lastName"),
                        resultSet.getByte("age"));
                user.setId(resultSet.getLong("id"));
/*              user.setId(resultSet.getLong(1));
                user.setName(resultSet.getString(2));
                user.setLastName(resultSet.getString(3));
                user.setAge(resultSet.getByte(4)); */
                users.add(user);

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    public void cleanUsersTable() {
        String tableName = "user";
        String sql = "TRUNCATE TABLE " + tableName;


        try (Statement stmt = connection.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}