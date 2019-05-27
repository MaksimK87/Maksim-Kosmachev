package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.accidenthistory;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.AbstractDAO;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.ConnectionPoolException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.EmptyResultSetException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.AccidentHistory;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Entity;
import by.epam.javawebproject.maksimkosmachev.carrental.util.SystemConfig;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AccidentHistoryDAOImpl extends AbstractDAO implements AccidentHistoryDAO {

    private static Logger logger = Logger.getLogger(AccidentHistory.class);

    static {
        try {
            PropertyConfigurator.configure(new FileInputStream(SystemConfig.log4JPropertyFile));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static final String FIND_ALL_HISTORIES = "SELECT * FROM car_rental.accident_history";
    private static final String FIND_HISTORY_BY_ID = FIND_ALL_HISTORIES+" WHERE car_rental.accident_history.accident_id=?";
    private static final String DELETE_HISTORY_BY_ID = "DELETE FROM car_rental.accident_history " +
            "WHERE car_rental.accident_history.accident_id=?";
    private static final String INSERT_HISTORY = "INSERT INTO car_rental.accident_history (damage_cost,is_guilty,accident_date) " +
            "VALUES(?,?,?)";
    private static final String UPDATE_HISTORY = "UPDATE car_rental.accident_history " +
            "SET car_rental.accident_history.damage_cost=?  WHERE car_rental.accident_history.accident_id=?;";

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    @Override
    public List<AccidentHistory> findAll() {
        Connection connection=null;
        PreparedStatement preparedStatement=null;
        List<AccidentHistory> historyList = null;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_ALL_HISTORIES);
            ResultSet resultSet = preparedStatement.executeQuery();
            historyList = convertResultSet(resultSet);
            closeResource(preparedStatement, connection);
        } catch (SQLException | EmptyResultSetException e) {
            logger.error("Exception while finding all accident histories" + e);
        } catch (ConnectionPoolException e) {
            logger.error("Exception in connection pool while finding all accident histories" + e);
        }
        finally {
            closeResource(preparedStatement, connection);
        }
        return historyList;
    }


    @Override
    public Entity findEntityById(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        AccidentHistory history=null;
        int historyIndex = 0; // collection "cars" has only one entity car with index=0;
        try {
            connection = getConnectionFromPool();
            preparedStatement = connection.prepareStatement(FIND_HISTORY_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<AccidentHistory> historyList = convertResultSet(resultSet);
            if (historyList.size() != 0) {
                history = historyList.get(historyIndex);
                return history;
            }
        } catch (SQLException | EmptyResultSetException e) {
            logger.error("Exception while getting accident history by id from data base" + e);
        } catch (ConnectionPoolException e) {
            logger.error("Exception while getting or releasing connection " + e);
        } finally {
            closeResource(preparedStatement, connection);
        }

        return null;
    }

    @Override
    public boolean delete(Entity entity) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        if (entity instanceof AccidentHistory && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(DELETE_HISTORY_BY_ID);
                preparedStatement.setInt(1, entity.getId());
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                logger.error("Exception while deleting history by id from data base" + e);
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
        if (entity instanceof AccidentHistory && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(INSERT_HISTORY, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setDouble(1, ((AccidentHistory) entity).getDamageCost());
                preparedStatement.setBoolean(2, ((AccidentHistory) entity).getGuilt());
                preparedStatement.setDate(3,
                        Date.valueOf(((AccidentHistory) entity).getAccidentDate().format(formatter)));

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
        PreparedStatement preparedStatement=null;
        if (entity instanceof AccidentHistory && entity != null) {
            try {
                connection = getConnectionFromPool();
                preparedStatement = connection.prepareStatement(UPDATE_HISTORY);
                preparedStatement.setDouble(1, ((AccidentHistory) entity).getDamageCost());
                preparedStatement.setInt(2,  entity.getId());
                preparedStatement.executeUpdate();

            } catch (ConnectionPoolException | SQLException e) {
                logger.error("Exception while updating entity " + e);
            }
            finally {
                closeResource(preparedStatement,connection);
            }
            return true;

        }
        return false;
    }

    private List<AccidentHistory> convertResultSet(ResultSet resultSet) throws EmptyResultSetException {
        List<AccidentHistory> historyList = new ArrayList<>();
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    AccidentHistory history = new AccidentHistory();
                    history.setId(resultSet.getInt("accident_id"));
                    history.setDamageCost(resultSet.getDouble("damage_cost"));
                    history.setGuilt(resultSet.getBoolean("is_guilty"));
                    history.setAccidentDate(resultSet.getTimestamp("accident_date")
                            .toLocalDateTime().toLocalDate());
                    historyList.add(history);
                }
            } catch (SQLException e) {
                logger.error("SQLException arisen while resultSet was converting accident history" + e);
            }
        } else {

            logger.error("Exception cause is empty resultSet ");
            throw new EmptyResultSetException();

        }
        return historyList;
    }

    public static void main(String[] args) {
        //Entity accident=new AccidentHistoryDAO(8,1,true,LocalDate.parse("2000-03-23"));

        AccidentHistoryDAOImpl accidentHistoryDAO=new AccidentHistoryDAOImpl();
        System.out.println(accidentHistoryDAO.findEntityById(3));
      //  System.out.println("id:"+ accident.getId());
    }
}
