package test.by.epam.javawebproject.maksimkosmachev.carrental.dao.connectionpool;


import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.Assert.*;

public class ConnectionPoolTest {
    ConnectionPool connectionPool;
    Connection connection;
    Logger logger=Logger.getLogger(this.getClass());
    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void setUp() throws Exception {
         connection = ConnectionPool.getPool().getConnection();
         connectionPool=ConnectionPool.getPool();

    }

    @After
    public void tearDown() throws Exception {
       ConnectionPool.getPool().returnConnection(connection);
    }


    @Test
    public void getConnection() {
        try {
            assertTrue(connection.isValid(1));
            assertFalse(connection.isClosed());
        } catch ( SQLException e) {
            e.printStackTrace();
        }

    }



}