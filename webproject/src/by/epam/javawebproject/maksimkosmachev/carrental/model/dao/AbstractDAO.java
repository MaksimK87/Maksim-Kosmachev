package by.epam.javawebproject.maksimkosmachev.carrental.model.dao;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.ConnectionPoolException;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;


public class AbstractDAO {

    private static Logger logger = Logger.getLogger(AbstractDAO.class);

    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    private ConnectionPool connectionPool = ConnectionPool.getPool();

    public Connection getConnectionFromPool() throws ConnectionPoolException {
        return connectionPool.getPool().getConnection();
    }

    public void releaseConnectionInPool(Connection connection) throws ConnectionPoolException {
        if (connection != null) {
            connectionPool.returnConnection(connection);
        }
    }

    public void closeConnection(Connection connection) throws ConnectionPoolException {
        connectionPool.closeConnection(connection);
    }

    public void closeAllConnections() throws ConnectionPoolException {
        connectionPool.closeAllConnections();
    }

//    public void closeStatement(Statement statement) {
//        try {
//            statement.close();
//        } catch (SQLException e) {
//            logger.error("Can not close statement " + e);
//        }
//    }

    public void closeResource(PreparedStatement preparedStatement, Connection connection) {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                releaseConnectionInPool(connection);
            }

        } catch (SQLException | ConnectionPoolException e) {
            logger.error("Exception while closing statement or releasing connection " + e);
        }
    }

}
