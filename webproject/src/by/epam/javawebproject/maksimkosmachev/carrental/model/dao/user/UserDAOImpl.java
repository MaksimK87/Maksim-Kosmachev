package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.user;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.AbstractDAO;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Entity;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.User;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.UserRole;
import by.epam.javawebproject.maksimkosmachev.carrental.model.exception.*;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOImpl extends AbstractDAO implements UserDAO {

    private static Logger logger = Logger.getLogger(UserDAOImpl.class);

    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String FIND_ALL_USERS = "SELECT * FROM car_rental.user";
    private static final String FIND_BY_LOGIN_PASSWORD = FIND_ALL_USERS + " WHERE user.login IN (?) AND user.password IN (?)";
    private static final String FIND_BY_LOGIN = FIND_ALL_USERS + " WHERE user.login IN (?)";
    private static final String FIND_USER_BY_ID = FIND_ALL_USERS + " WHERE user.user_id=?";
    private static final String DELETE_USER = "DELETE FROM car_rental.user WHERE user_id=?";
    private static final String UPDATE_USER = "UPDATE car_rental.user SET login=?, " +
            "password=?, name=?, surname=?, passport_number=? ,phone_number=?" +
            " WHERE user_id=?";
    private static final String INSERT_USER = "INSERT INTO car_rental.user " +
            "(login,password,name,surname,passport_number,phone_number,role) " +
            "VALUES (?,?,?,?,?,?,?)";


    @Override
    public void registration(Entity entity) throws SuchUserExistsException, DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_BY_LOGIN);
            preparedStatement.setString(1, ((User) entity).getLogin());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                logger.error("Such user already exists! Try to use another login for registration");
                throw new SuchUserExistsException();

            } else {
                connection.setAutoCommit(false);
                preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                preparedStatement = insertUserStatement(preparedStatement, entity);
                connection.setAutoCommit(true);
                ResultSet genetatedKey = preparedStatement.getGeneratedKeys();
                if (genetatedKey.next()) {
                    entity.setId(genetatedKey.getInt(1));
                }
            }
        } catch (ConnectionPoolException e) {
            logger.error("SQL exception has occurred while user registration" + e);
        } catch (SQLException e) {
            logger.error("SQL exception has occurred while user registration" + e);
            throw new DAOException();
        } finally {
            closeResource(preparedStatement, connection);
        }

    }

    @Override
    public User signIn(String login, String password) throws SuchUserNotExistException, DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        User user = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_BY_LOGIN_PASSWORD);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user = getUserFromResultSet(resultSet, user);
            } else {
                System.out.println("Such account does not exist");
                throw new SuchUserNotExistException();
            }

        } catch (SQLException e) {
            logger.error("SQL exception has occurred while user registration" + e);
            throw new DAOException();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            closeResource(preparedStatement, connection);
        }
        return user;
    }

    @Override
    public List<User> findAll() throws EmptyResultSetException, DAOException {
        List<User> users = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ALL_USERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet != null) {
                try {
                    while (resultSet.next()) {
                        User user = new User();
                        user = getUserFromResultSet(resultSet, user);
                        users.add(user);
                    }
                } catch (SQLException e) {
                    logger.error("SQL exception has occurred while finding all users" + e);
                    throw new DAOException();
                }
            } else {
                logger.error("Exception cause is empty resultSet");
                throw new EmptyResultSetException();

            }

        } catch (SQLException e) {
            logger.error("SQL exception has occurred while finding all users" + e);
            throw new DAOException();
        } catch (ConnectionPoolException e) {
            e.printStackTrace();
        } finally {
            closeResource(preparedStatement, connection);
        }

        return users;
    }

    @Override
    public Entity findEntityById(int id) throws SuchUserNotExistException {
        User user = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                user = new User();
                user = getUserFromResultSet(resultSet, user);

            } else {
                logger.error("Such user wasn't found by id");
                throw new SuchUserNotExistException();
            }
        } catch (SQLException e) {
            logger.error("Exception while getting car by id from data base" + e);
        } catch (ConnectionPoolException e) {
            logger.error("Exception while getting or releasing connection " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }

        return user;
    }

    @Override
    public boolean delete(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (entity instanceof User && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(DELETE_USER);
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Exception while deleting user by id from data base" + e);
            } catch (ConnectionPoolException e) {
                logger.error("Exception while getting or releasing connection " + e);
            } finally {
                closeResource(preparedStatement, connection);
            }

            return true;

        }
        return false;
    }

    @Override
    public boolean insert(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (entity instanceof User && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
                preparedStatement = insertUserStatement(preparedStatement, entity);
                ResultSet genetatedKey = preparedStatement.getGeneratedKeys();
                if (genetatedKey.next()) {
                    entity.setId(genetatedKey.getInt(1));
                }

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while inserting user " + e);
            } finally {
                closeResource(preparedStatement, connection);
            }
            return true;

        }
        return false;
    }

    @Override
    public boolean update(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (entity instanceof User && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(UPDATE_USER);
                preparedStatement.setString(1, ((User) entity).getLogin());
                preparedStatement.setString(2, ((User) entity).getPassword());
                preparedStatement.setString(3, ((User) entity).getName());
                preparedStatement.setString(4, ((User) entity).getSurname());
                preparedStatement.setString(5, ((User) entity).getPassportNumber());
                preparedStatement.setString(6, ((User) entity).getPhoneNumber());
                preparedStatement.setInt(7, ((User) entity).getId());
                preparedStatement.executeUpdate();

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while updating user " + e);
            } finally {
                closeResource(preparedStatement, connection);
            }
            return true;

        }
        return false;
    }

    private PreparedStatement insertUserStatement(PreparedStatement preparedStatement, Entity entity) throws SQLException {
        preparedStatement.setString(1, ((User) entity).getLogin());
        preparedStatement.setString(2, ((User) entity).getPassword());
        preparedStatement.setString(3, ((User) entity).getName());
        preparedStatement.setString(4, ((User) entity).getSurname());
        preparedStatement.setString(5, ((User) entity).getPassportNumber());
        preparedStatement.setString(6, ((User) entity).getPhoneNumber());
        preparedStatement.setInt(7, ((User) entity).getUserRole().getRoleCode());
        preparedStatement.executeUpdate();
        return preparedStatement;
    }

    private User getUserFromResultSet(ResultSet resultSet, User user) throws SQLException {
        user.setId(resultSet.getInt("user_id"));
        user.setLogin(resultSet.getString("login"));
        user.setPassword(resultSet.getString("password"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setPassportNumber(resultSet.getString("passport_number"));
        user.setPhoneNumber(resultSet.getString("phone_number"));
        user.setUserRole(UserRole.getRoleByCode(resultSet.getInt("role")));
        return user;
    }

    public static void main(String[] args) throws SuchUserExistsException, DAOException, SuchUserNotExistException {
        User user = new User(7, "Alex", "3456", "Артем", "Бобок", "PP987654",
                "090909", UserRole.CLIENT);
        UserDAOImpl userDAO = new UserDAOImpl();
        userDAO.update(user);
        //userDAO.registration(user);
        System.out.println(userDAO.signIn("Admin", "12345"));
    }
}
