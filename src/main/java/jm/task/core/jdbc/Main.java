package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

public class Main {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
/**
 * Создание таблицы User(ов)
 */
        userService.dropUsersTable();
        userService.createUsersTable();
/**
 * Добавление 4 User(ов) в таблицу с данными на свой выбор. После каждого добавления должен быть вывод в консоль ( User с именем – name добавлен в базу данных )
 */
        userService.saveUser("Jason", "Hanson", (byte) 40);
        userService.saveUser("Christopher", "Smith", (byte) 27);
        userService.saveUser("Mary", "Sandoval", (byte) 35);
        userService.saveUser("Kimberly", "Graham", (byte) 25);
/**
 * Удаление User по его id
  */
//        userService.removeUserById(3);
/**
 * Получение всех User из базы и вывод в консоль ( должен быть переопределен toString в классе User)
 */
        System.out.println(userService.getAllUsers());
/**
 * Очистка таблицы User(ов)
 */
        userService.cleanUsersTable();
/**
 * Удаление таблицы
 */
        userService.dropUsersTable();
    }
}