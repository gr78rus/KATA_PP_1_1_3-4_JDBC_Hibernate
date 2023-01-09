package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getMySQLConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS user(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)");
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getMySQLConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DROP TABLE IF EXISTS user");
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getMySQLConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate(String.format("INSERT INTO user(name, lastName, age) VALUES ('%s', '%s', %s)", name, lastName, age));
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getMySQLConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM user WHERE id=" + id);
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Connection connection = Util.getMySQLConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setName(resultSet.getString("name"));
                user.setLastName(resultSet.getString("lastName"));
                user.setAge(resultSet.getByte("age"));
                userList.add(user);
            }
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getMySQLConnection()) {
            Statement statement = connection.createStatement();
            statement.executeUpdate("TRUNCATE TABLE user");
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }
}