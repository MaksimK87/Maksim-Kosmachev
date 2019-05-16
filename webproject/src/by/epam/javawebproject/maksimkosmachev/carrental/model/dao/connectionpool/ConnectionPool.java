package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.connectionpool;

import by.epam.javawebproject.maksimkosmachev.carrental.model.exception.ConnectionPoolException;
import by.epam.javawebproject.maksimkosmachev.carrental.util.DBConnectionConfig;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPool {

    private static Logger logger = Logger.getLogger(ConnectionPool.class);


    private static final String DB_DRIVER = DBConnectionConfig.JDBC_MYSQL_DRIVER;
    private static final String DB_URL = DBConnectionConfig.DATABASE_URL;
    private static final String DB_USER = DBConnectionConfig.DATABASE_USER;
    private static final String DB_PASSWORD = DBConnectionConfig.PASSWORD;
    private static final int CONNECTIONS_QUANTITY = DBConnectionConfig.CONNECTION_QUANTITY;
    private static ConnectionPool connectionPool = null;
    private BlockingQueue<Connection> availableConnections;
    private ArrayList<Connection> usingConnections;

    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private ConnectionPool() {
        availableConnections = new ArrayBlockingQueue<>(CONNECTIONS_QUANTITY);
        usingConnections = new ArrayList<>();
        try {
            initConnection();
        } catch (ConnectionPoolException e) {
            logger.error("exception while creating ConnectionPool instance " + e);
        }
        logger.debug("connection pool has been created");

    }

    public static ConnectionPool getPool() {
        if (connectionPool == null) {
            synchronized (ConnectionPool.class) {
                if (connectionPool == null) {
                    connectionPool = new ConnectionPool();
                }
            }
        }
        return connectionPool;
    }

    private void initConnection() throws ConnectionPoolException {
        try {
            Class.forName(DB_DRIVER);
            for (int i = 0; i < CONNECTIONS_QUANTITY; i++) {
                Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                availableConnections.add(connection);
            }
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("exception in initializing ConnectionPool " + e);
            throw new ConnectionPoolException();

        }
    }

    public Connection getConnection() throws ConnectionPoolException {
        Connection connection = null;
        if (availableConnections.size() > 0) {
            try {
                connection = availableConnections.take();
                if (connection != null && connection.isValid(0)) {
                    usingConnections.add(connection);
                }
            } catch (InterruptedException | SQLException e) {
                logger.error("exception while getting connection from available connections in ConnectionPool" + e);
                throw new ConnectionPoolException();
            }

        } else {
            try {
                connection = createConnection();
            } catch (ConnectionPoolException e) {
                logger.error("exception while creating new connection in ConnectionPool " + e);
                throw new ConnectionPoolException();
            }
            usingConnections.add(connection);
        }
        return connection;
    }

    public void returnConnection(Connection connection) throws ConnectionPoolException {
        if (connection != null) {
            try {
                connection.setAutoCommit(true);
                if (availableConnections.size() < CONNECTIONS_QUANTITY) {
                    availableConnections.put(connection);
                    usingConnections.remove(connection);
                }
            } catch (SQLException | InterruptedException e) {
                logger.error("exception while returning connection in ConnectionPool " + e);
                throw new ConnectionPoolException();
            }
        }
    }


    private Connection createConnection() throws ConnectionPoolException {
        Connection connection = null;
        try {
            Class.forName(DB_DRIVER);
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            logger.error("exception while creating connection " + e);
            throw new ConnectionPoolException();
        }
        return connection;
    }

    public void closeConnection(Connection connection) throws ConnectionPoolException {
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error("exception while closing connection " + e);
            throw new ConnectionPoolException();
        }
    }

    public void closeAllConnections() throws ConnectionPoolException {
        for (int i = 0; i < usingConnections.size(); i++) {
            try {
                usingConnections.get(i).close();
            } catch (SQLException e) {
                logger.error("exception while closing all connections " + e);
                throw new ConnectionPoolException();
            }
        }
    }

}
