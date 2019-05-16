package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.car;

import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Car;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.BodyType;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Entity;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.Manufacturer;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.TransmissionType;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.AbstractDAO;
import by.epam.javawebproject.maksimkosmachev.carrental.model.exception.ConnectionPoolException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.exception.EmptyResultSetException;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarDAOImpl extends AbstractDAO implements CarDAO {

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

    private static Logger logger = Logger.getLogger(CarDAOImpl.class);

    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getCarByManufacturer(Manufacturer manufacturer) {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        List<Car> cars = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_BY_MANUFACTURER);
            preparedStatement.setString(1, manufacturer.toString());
            ResultSet resultSet = preparedStatement.executeQuery();
            cars = convertResultSet(resultSet);


        } catch (EmptyResultSetException | ConnectionPoolException | SQLException e) {
            logger.error("Exception while getting all cars from database " + e);
        }
        finally {
            closeResource(preparedStatement,connection);
        }
        return cars;
    }

    @Override
    public List<Car> getCarByYear(int yearFrom, int yearTo) {
        List<Car> cars = null;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_BY_YEAR);
            preparedStatement.setInt(1, yearFrom);
            preparedStatement.setInt(2, yearTo);
            ResultSet resultSet = preparedStatement.executeQuery();
            cars = convertResultSet(resultSet);
        } catch (ConnectionPoolException | SQLException | EmptyResultSetException e) {
            logger.error("Exception while searching car by year in database " + e);
        }
        finally {
            closeResource(preparedStatement,connection);
        }
        return cars;
    }

    @Override
    public List<Car> findAll() {
        List<Car> cars = null;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            cars = convertResultSet(resultSet);

        } catch (EmptyResultSetException | ConnectionPoolException | SQLException e) {
            logger.error("Empty result set " + e);
        }
        finally {
            closeResource(preparedStatement,connection);
        }

        return cars;
    }

    @Override
    public Entity findEntityById(int id) {
        Car car ;
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        int carIndex = 0; // collection "cars" has only one entity car with index=0;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Car> cars = convertResultSet(resultSet);
            if (cars.size() != 0) {
                car = cars.get(carIndex);
                return car;
            }
        } catch (SQLException | EmptyResultSetException e) {
            logger.error("Exception while getting car by id from data base" + e);
        } catch (ConnectionPoolException e) {
            logger.error("Exception while getting or releasing connection " + e);
        }
        finally {
            closeResource(preparedStatement,connection);
        }

        return null;
    }


    @Override
    public boolean delete(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        if (entity instanceof Car && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(DELETE_BY_ID);
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Exception while deleting car by id from data base" + e);
            } catch (ConnectionPoolException e) {
                logger.error("Exception while getting or releasing connection " + e);
            }
            finally {
                closeResource(preparedStatement,connection);
            }

            return true;

        }
        return false;
    }

    @Override
    public boolean insert(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (entity instanceof Car && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, ((Car) entity).getManufacturer().toString());
                preparedStatement.setString(2, ((Car) entity).getModel());
                preparedStatement.setInt(3, ((Car) entity).getYearIssue());
                preparedStatement.setString(4, ((Car) entity).getBodyType().toString());
                preparedStatement.setString(5, ((Car) entity).getTransmissionType().toString());
                preparedStatement.setDouble(6, ((Car) entity).getEngineValue());
                preparedStatement.setDouble(7, ((Car) entity).getRentPrice());
                preparedStatement.executeUpdate();
                ResultSet genetatedKey = preparedStatement.getGeneratedKeys();
                if (genetatedKey.next()) {
                    entity.setId(genetatedKey.getInt(1));
                }

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while inserting car " + e);
            } finally {
                closeResource(preparedStatement,connection);
            }

            return true;

        }
        return false;
    }

    @Override
    public boolean update(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement=null;
        if (entity instanceof Car && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(UPDATE);
                preparedStatement.setDouble(1, ((Car) entity).getRentPrice());
                preparedStatement.setInt(2, ((Car) entity).getId());
                preparedStatement.executeUpdate();

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while updating car " + e);
            }
            finally {
                closeResource(preparedStatement,connection);
            }
            return true;

        }
        return false;
    }

    private List<Car> convertResultSet(ResultSet resultSet) throws EmptyResultSetException {
        List<Car> cars = new ArrayList<>();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Car car = new Car();
                    car.setId(resultSet.getInt("car_id"));
                    car.setManufacturer(Manufacturer.valueOf(resultSet.getString("manufacturer")));
                    car.setModel(resultSet.getString("model"));
                    car.setYearIssue(resultSet.getInt("year"));
                    car.setBodyType(BodyType.valueOf(resultSet.getString("body_type")));
                    car.setTransmissionType(TransmissionType.valueOf(resultSet.getString("transmission_type")));
                    car.setEngineValue(resultSet.getDouble("engine_value"));
                    car.setRentPrice(resultSet.getDouble("rent_price"));
                    cars.add(car);
                }
            } catch (SQLException e) {
                logger.error("SQLException arisen while resultSet was converting" + e);
            }
        } else {
            logger.error("Exception cause is empty resultSet");
            throw new EmptyResultSetException();

        }
        return cars;
    }
}
