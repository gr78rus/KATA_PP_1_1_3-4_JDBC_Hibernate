package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private static final SessionFactory sessionFactory = Util.getSessionFactory();

    public UserDaoHibernateImpl() {

    }

    @Override
    public void createUsersTable() {
        String sqlCreateUsersTable = "CREATE TABLE IF NOT EXISTS user(id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(50), lastName VARCHAR(50), age INT)";
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sqlCreateUsersTable).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void dropUsersTable() {
        String sqlDropUsersTable = "DROP TABLE IF EXISTS user";
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.createSQLQuery(sqlDropUsersTable).executeUpdate();
            transaction.commit();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = null;
            try {
                transaction = session.beginTransaction();
                session.persist(user);
                transaction.commit();
            } catch (Exception e) {
                if (transaction != null) transaction.rollback();
                e.printStackTrace();
                }
            }
        }

        @Override
        public void removeUserById ( long id){
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = null;
                try {
                    transaction = session.beginTransaction();
                    String sqlRemoveUserById = "DELETE User WHERE id=:id";
                    session.createQuery(sqlRemoveUserById).setParameter("id", id).executeUpdate();
                    transaction.commit();
                } catch (Exception e) {
                    if (transaction != null) transaction.rollback();
                    e.printStackTrace();
                }
            }
        }

        @Override
        public List<User> getAllUsers () {
            try (Session session = sessionFactory.openSession()) {
                return session.createQuery("from User", User.class).list();
            }
        }

        @Override
        public void cleanUsersTable () {
            String sqlCleanUsersTable = "TRUNCATE TABLE user";
            try (Session session = sessionFactory.openSession()) {
                Transaction transaction = session.beginTransaction();
                session.createSQLQuery(sqlCleanUsersTable).executeUpdate();
                transaction.commit();
            }
        }
    }