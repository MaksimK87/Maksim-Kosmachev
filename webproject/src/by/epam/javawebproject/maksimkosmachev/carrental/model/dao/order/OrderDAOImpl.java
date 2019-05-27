package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.order;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.AbstractDAO;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Car;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Entity;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Order;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.User;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.*;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.ConnectionPoolException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.SuchOrderNotExistsException;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl extends AbstractDAO implements OrderDAO {
    private static final String FIND_ALL_ORDERS = "SELECT * FROM car_rental.order";
    private static final String FIND_ORDER_BY_ID = FIND_ALL_ORDERS + " WHERE order_id=?";
    private static final String FIND_ORDER_BY_USER_ID = FIND_ALL_ORDERS + " WHERE user_id=?";
    private static final String FIND_ORDER_BY_CAR_ID = FIND_ALL_ORDERS + " WHERE car_id=?";
    private static final String DELETE_ORDER = " DELETE FROM car_rental.order WHERE order_id=?";
    private static final String INSERT_USER_CHOICE = "INSERT INTO car_rental.order (rent_terms,rent_from_date,car_id,user_id) " +
            "VALUES(?,?,?,?)";
    private static final String UPDATE_TOTAL_SUM = "UPDATE car_rental.order SET total_sum=? WHERE order_id=?";
    private static final String PROCESS_ORDER = "UPDATE car_rental.order SET accept_order=? WHERE order_id=?";
    private static final String SET_CAR_CONDITION = "UPDATE car_rental.order SET condition_after_refund=? WHERE order_id=?";
    private static final String JUSTIFY_REFUSE = "UPDATE car_rental.order SET refusal_reason=? WHERE order_id=?";

    //private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm");
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    private static Logger logger = Logger.getLogger(OrderDAOImpl.class);

    {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findByUser(User user) throws SuchOrderNotExistsException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order order = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ORDER_BY_USER_ID);
            preparedStatement.setInt(1, user.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            } else {
                logger.error("Order with such user wasn't found!");
                throw new SuchOrderNotExistsException();
            }


        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while finding order by user id from database " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
        return order;
    }

    @Override
    public Order findByCar(Car car) throws SuchOrderNotExistsException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order order = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ORDER_BY_CAR_ID);
            preparedStatement.setInt(1, car.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            } else {
                logger.error("Order with such car wasn't found!");
                throw new SuchOrderNotExistsException();
            }

        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while finding order by user id from database " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
        return order;
    }

    @Override
    public void processOrder(boolean decision, Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        order.setAcceptOrder(decision);
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(PROCESS_ORDER);
            preparedStatement.setBoolean(1, order.getAcceptOrder());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while processing order " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
    }

    @Override
    public void setCarCondition(Condition condition, Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        order.setConditionAfterRefund(condition);
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(SET_CAR_CONDITION);
            preparedStatement.setString(1, order.getConditionAfterRefund().toString());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while setting car condition in order " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
    }

    @Override
    public void justifyRefuse(String refuse, Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        order.setRefusalReason(refuse);
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(JUSTIFY_REFUSE);
            preparedStatement.setString(1, order.getRefusalReason());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException |
                SQLException e) {
            logger.error("Exception while justifying refusal" + e);
        } finally {
            closeResource(preparedStatement, connection);
        }

    }

    @Override
    public void updateTotalSum(Double totalSum, Order order) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        order.setTotalSum(totalSum);
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(UPDATE_TOTAL_SUM);
            preparedStatement.setDouble(1, order.getTotalSum());
            preparedStatement.setInt(2, order.getId());
            preparedStatement.executeUpdate();
        } catch (ConnectionPoolException |
                SQLException e) {
            logger.error("Exception while updating total sum of order" + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order order = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ALL_ORDERS);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
                orders.add(order);
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while finding all orders from database " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
        return orders;
    }

    @Override
    public Entity findEntityById(int id) throws SuchOrderNotExistsException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Order order = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                order = getOrderFromResultSet(resultSet);
            } else {
                logger.error("Order with such car wasn't found!");
                throw new SuchOrderNotExistsException();
            }
        } catch (ConnectionPoolException | SQLException e) {
            logger.error("Exception while finding all orders from database " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }
        return order;
    }

    @Override
    public boolean delete(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (entity instanceof Order && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(DELETE_ORDER);
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.executeUpdate();
            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while deleting order from database " + e);
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
        if (entity instanceof Order && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(INSERT_USER_CHOICE, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setInt(1, (((Order) entity).getRentTerm()));
                preparedStatement.setDate(2,
                        Date.valueOf(((Order) entity).getRentFromDate().format(formatter)));
                preparedStatement.setInt(3, (((Order) entity).getCarId()));
                preparedStatement.setInt(4, (((Order) entity).getUserId()));
                preparedStatement.executeUpdate();
                ResultSet generatedKey = preparedStatement.getGeneratedKeys();
                if (generatedKey.next()) {
                    entity.setId(generatedKey.getInt(1));
                }
            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while inserting order to database " + e);
            } finally {
                closeResource(preparedStatement, connection);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean update(Entity entity) {
        return false;
    }

    private Order getOrderFromResultSet(ResultSet resultSet) throws SQLException {
        Order order = new Order();
        order.setId(resultSet.getInt("order_id"));
        order.setRentTerm(resultSet.getInt("rent_terms"));
        order.setTotalSum(resultSet.getDouble("total_sum"));
        order.setRentFromDate(resultSet.getDate("rent_from_date").toLocalDate());
        order.setConditionAfterRefund(Condition.
                valueOf(resultSet.getString("condition_after_refund").toUpperCase()));
        order.setRefundDate(resultSet.getDate("refund_date").toLocalDate());
        order.setAcceptOrder(resultSet.getBoolean("accept_order"));
        order.setRefusalReason(resultSet.getString("refusal_reason"));
        order.setCarId(resultSet.getInt("car_id"));
        order.setUserId(resultSet.getInt("user_id"));
        return order;
    }

    public static void main(String[] args) throws SuchOrderNotExistsException {
        User user = new User(7, "Alex", "3456", "Артем", "Бобок", "PP987654",
                "090909", UserRole.CLIENT);
        Car car=new Car(0,Manufacturer.VOLKSWAGEN,"T5",2015, BodyType.MINIVAN, TransmissionType.AUTOMATIC_TRANSMISSION,
                2.5,100);
        Order order=new Order(5,500, LocalDate.of(2019,05,15),true
                ,Condition.GOOD,LocalDate.of(2019,05,20),
                true,null);
        order.setId(8);
        order.setCar(car);
        order.setUser(user);
        OrderDAOImpl orderDAO=new OrderDAOImpl();
       System.out.println(orderDAO.findAll());
//     //  orderDAO.insert(order);
//       // orderDAO.updateTotalSum(180.0,order);
//        //orderDAO.delete(order);
//        //orderDAO.setCarCondition(Condition.GOOD,order);
//        orderDAO.processOrder(false,order);
//        orderDAO.justifyRefuse("You had accident last time",order);
      //  System.out.println(orderDAO.findAll());
    }
}
