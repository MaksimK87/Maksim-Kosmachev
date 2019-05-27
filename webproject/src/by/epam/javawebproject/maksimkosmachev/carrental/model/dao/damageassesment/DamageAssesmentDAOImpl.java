package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.damageassesment;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.AbstractDAO;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.ConnectionPoolException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.EmptyResultSetException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.DamageAssessment;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Entity;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Order;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DamageAssesmentDAOImpl extends AbstractDAO implements DamageAssesmentDAO {

    private static Logger logger = Logger.getLogger(DamageAssesmentDAOImpl.class);

    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String FIND_ALL_DAMAGE = "SELECT * FROM car_rental.damage_assessment";
    private static final String FIND_DAMAGE_BY_ID = FIND_ALL_DAMAGE + " WHERE " +
            "car_rental.damage_assessment.damage_assessment_id=?";
    private static final String FIND_DAMAGE_BY_ORDER_ID = FIND_ALL_DAMAGE +
            " WHERE car_rental.damage_assessment.order_id=?";
    private static final String DELETE_DAMAGE = "DELETE FROM car_rental.damage_assessment " +
            "WHERE car_rental.damage_assessment.damage_assessment_id=?";
    private static final String INSERT_DAMAGE = "INSERT INTO car_rental.damage_assessment (car_hood_cost" +
            ",car_boot_cost,front_door_cost,rear_door_cost,fender_cost,order_id) " +
            "VALUES(?,?,?,?,?,?)";
    private static final String UPDATE_DAMAGE = "UPDATE car_rental.damage_assessment " +
            "SET car_rental.damage_assessment.car_hood_cost=?, car_rental.damage_assessment.car_boot_cost=?," +
            " car_rental.damage_assessment.front_door_cost=? ,car_rental.damage_assessment.rear_door_cost=?," +
            "car_rental.damage_assessment.fender_cost=? WHERE car_rental.damage_assessment.damage_assessment_id=?;";

    @Override
    public List<DamageAssessment> findAll() {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        List<DamageAssessment> damages = new ArrayList<>();
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ALL_DAMAGE);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                damages.add(convertResultSet(resultSet));
            }
        } catch (SQLException | EmptyResultSetException e) {
            logger.error("Exception while finding all accident histories" + e);
        } catch (ConnectionPoolException e) {
            logger.error("Exception in connection pool while finding all accident histories" + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
        return damages;
    }

    @Override
    public Entity findEntityById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        DamageAssessment damageAssessment = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_DAMAGE_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                damageAssessment = convertResultSet(resultSet);
            }
        } catch (SQLException | EmptyResultSetException e) {
            logger.error("Exception while finding damageAssessment by id" + e);
        } catch (ConnectionPoolException e) {
            logger.error("Exception in connection pool while finding damageAssessment by id" + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
        return damageAssessment;
    }

    @Override
    public boolean delete(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (entity instanceof DamageAssessment && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(DELETE_DAMAGE);
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.executeUpdate();

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while inserting entity " + e);
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
        if (entity instanceof DamageAssessment && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(INSERT_DAMAGE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setDouble(1, ((DamageAssessment) entity).getCarHood());
                preparedStatement.setDouble(2, ((DamageAssessment) entity).getCarBoot());
                preparedStatement.setDouble(3, ((DamageAssessment) entity).getFrontDoor());
                preparedStatement.setDouble(4, ((DamageAssessment) entity).getRearDoor());
                preparedStatement.setDouble(5, ((DamageAssessment) entity).getFender());
                preparedStatement.setInt(6, ((DamageAssessment) entity).getOrderId());
                preparedStatement.executeUpdate();
                ResultSet generatedKey = preparedStatement.getGeneratedKeys();
                if (generatedKey.next()) {
                    entity.setId(generatedKey.getInt(1));
                }

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while inserting entity " + e);
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
        if (entity instanceof DamageAssessment && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(UPDATE_DAMAGE);
                preparedStatement.setDouble(1, ((DamageAssessment) entity).getCarHood());
                preparedStatement.setDouble(2, ((DamageAssessment) entity).getCarBoot());
                preparedStatement.setDouble(3, ((DamageAssessment) entity).getFrontDoor());
                preparedStatement.setDouble(4, ((DamageAssessment) entity).getRearDoor());
                preparedStatement.setDouble(5, ((DamageAssessment) entity).getFender());
                preparedStatement.setInt(6, entity.getId());
                preparedStatement.executeUpdate();

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while updating entity " + e);
            } finally {
                closeResource(preparedStatement, connection);
            }
            return true;

        }
        return false;
    }

    private DamageAssessment convertResultSet(ResultSet resultSet) throws EmptyResultSetException {
        DamageAssessment damageAssessment = new DamageAssessment();
        if (resultSet != null) {
            try {
                damageAssessment.setId(resultSet.getInt("damage_assessment_id"));
                damageAssessment.setCarHood(resultSet.getDouble("car_hood_cost"));
                damageAssessment.setCarBoot(resultSet.getDouble("car_boot_cost"));
                damageAssessment.setFrontDoor(resultSet.getDouble("front_door_cost"));
                damageAssessment.setRearDoor(resultSet.getDouble("rear_door_cost"));
                damageAssessment.setFender(resultSet.getDouble("fender_cost"));
                damageAssessment.setOrderId(resultSet.getInt("order_id"));
            } catch (SQLException e) {
                logger.error("SQLException arisen while resultSet was converting damage assessment" + e);
            }
        } else {

            logger.error("Exception cause is empty resultSet ");
            throw new EmptyResultSetException();

        }
        return damageAssessment;
    }

    @Override
    public DamageAssessment getDamageByOrderId(Order order) {
        DamageAssessment damageAssessment = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_DAMAGE_BY_ORDER_ID);
            preparedStatement.setInt(1, order.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                damageAssessment = convertResultSet(resultSet);
            }
        } catch (SQLException | EmptyResultSetException e) {
            logger.error("Exception while finding damageAssessment by id" + e);
        } catch (ConnectionPoolException e) {
            logger.error("Exception in connection pool while finding damageAssessment by id" + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
        return damageAssessment;
    }

    public static void main(String[] args) {
        DamageAssesmentDAOImpl damageAssesmentDAO = new DamageAssesmentDAOImpl();
//        DamageAssessment damage; //= new DamageAssessment(11, 22, 32, 42, 44);
//       Order order = new Order(5, 500, LocalDate.of(2019, 05, 15), true
//                , Condition.GOOD, LocalDateTime.of(2019, 05, 20, 14, 20),
//                true, null);
//        damage=new DamageAssessment();
//        damage.setOrder(order);
//        damage.setOrderId(99);
//        damage.setId(4);
//        System.out.println(damageAssesmentDAO.findEntityById(4));
        List<DamageAssessment> list;
        list=damageAssesmentDAO.findAll();
        System.out.println(list);

    }

}
