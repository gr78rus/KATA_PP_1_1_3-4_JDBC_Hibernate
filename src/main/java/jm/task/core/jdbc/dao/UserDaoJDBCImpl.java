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
        String sqlCreateUsersTable = "CREATE TABLE IF NOT EXISTS user(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)";
        try (Statement statement = Util.getMySQLConnection().createStatement()) {
            statement.execute(sqlCreateUsersTable);
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public void dropUsersTable() {
        String sqlDropUsersTable = "DROP TABLE IF EXISTS user";
        try (Statement statement = Util.getMySQLConnection().createStatement()) {
            statement.execute(sqlDropUsersTable);
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sqlSaveUser = "INSERT INTO user (name, lastName, age) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = Util.getMySQLConnection().prepareStatement(sqlSaveUser)) {
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, lastName);
            preparedStatement.setByte(3, age);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public void removeUserById(long id) {
        String sqlRemoveUserById = "DELETE FROM user WHERE id=?";
        try (PreparedStatement preparedStatement = Util.getMySQLConnection().prepareStatement(sqlRemoveUserById)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }

    public List<User> getAllUsers() {
        String sqlGetAllUsers = "SELECT * FROM user";
        List<User> userList = new ArrayList<>();
        try (PreparedStatement preparedStatement = Util.getMySQLConnection().prepareStatement(sqlGetAllUsers)) {
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
        String sqlCleanUsersTable = "TRUNCATE TABLE user";
        try (Statement statement = Util.getMySQLConnection().createStatement()) {
            statement.executeUpdate(sqlCleanUsersTable);
        } catch (SQLException | ClassNotFoundException | RuntimeException exception) {
            System.out.println("Connection failed... " + exception);
        }
    }
}