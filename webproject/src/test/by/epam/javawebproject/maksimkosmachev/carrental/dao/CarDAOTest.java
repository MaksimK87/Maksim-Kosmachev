package test.by.epam.javawebproject.maksimkosmachev.carrental.dao;


import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Car;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.BodyType;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.Manufacturer;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.TransmissionType;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.car.CarDAOImpl;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.connectionpool.ConnectionPool;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.List;

import static org.junit.Assert.*;


public class CarDAOTest {

    private static final String FIND_ALL = "SELECT car.car_id, car.manufacturer, car.model, car.year, \n" +
            "            car.body_type, car.transmission_type,  \n" +
            "             car.engine_value,\n" +
            "           car.rent_price FROM car ";
    private static final String FIND_BY_ID = FIND_ALL + "WHERE car.car_id=?";
    private static final String FIND_BY_MANUFACTURER = FIND_ALL + " WHERE car.manufacturer IN (?)";
    private static final String FIND_BY_YEAR = FIND_ALL + "WHERE car.year BETWEEN ? AND ?;";
    private static final String DELETE_BY_ID = "DELETE FROM car WHERE car_id=?";
    private static final String INSERT = "INSERT INTO car (manufacturer,model,year,body_type,transmission_type," +
            "engine_value,rent_price) VALUES(?,?,?,?,?,?,?)";
    private static final String UPDATE = "UPDATE car SET rent_price=? WHERE car_id=?";


    Connection connection;
    ConnectionPool connectionPool = ConnectionPool.getPool();
    CarDAOImpl carDAO;
    List<Car> cars;

    private static Logger logger = Logger.getLogger(CarDAOTest.class);

    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    Car car = new Car(22, Manufacturer.BMW, "E34", 1998, BodyType.SEDAN, TransmissionType.AUTOMATIC_TRANSMISSION
            , 2.0, 30);

    @Before
    public void setUp() throws Exception {
        connection= connectionPool.getConnection();
        carDAO = new CarDAOImpl();

    }

    @After
    public void tearDown() throws Exception {
        connectionPool.returnConnection(connection);
        carDAO = null;
    }



    @Test
    public void findAll() {
        try {
            PreparedStatement preparedStatement=connection.prepareStatement("SELECT COUNT(*) FROM car");
            ResultSet resultSet=preparedStatement.executeQuery();
            resultSet.next();
            int quantity=resultSet.getInt(1);
            logger.info("quantity "+quantity);
            cars=carDAO.findAll();
            assertTrue(quantity==cars.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }





    @Test
    public void create() {
        assertTrue(carDAO.insert(car));

    }
    @Test
    public void getCarByManufacturer() {
        cars = carDAO.getCarByManufacturer(Manufacturer.BMW);
        assertTrue(cars.get(0).getManufacturer().equals(car.getManufacturer()));
    }

    @Test
    public void getCarByYear() {
        int year = 1998;
        cars = carDAO.getCarByYear(year, year);
        assertTrue(car.getYearIssue() == cars.get(0).getYearIssue());

    }
    @Test
    public void findEntityById() {
        Car carExpected= (Car) carDAO.findEntityById(22);
        assertTrue(car.equals(carExpected));
    }

    @Test
    public void update() {
        car.setRentPrice(45);
        carDAO.update(car);
    }
    @Test
    public void delete() {
        carDAO.delete(car);
        assertTrue(carDAO.findEntityById(18)==null);
    }
}