package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.daofactory;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.accidenthistory.AccidentHistoryDAOImpl;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.car.CarDAOImpl;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.damageassesment.DamageAssesmentDAOImpl;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.order.OrderDAOImpl;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.user.UserDAOImpl;

public class DAOFactory {

    public static final DAOFactory instance = new DAOFactory();
    
    private final CarDAOImpl carDAO = new CarDAOImpl();
    private final AccidentHistoryDAOImpl accidentHistoryDAO=new AccidentHistoryDAOImpl();
    private final DamageAssesmentDAOImpl damageAssesmentDAO=new DamageAssesmentDAOImpl();
    private final OrderDAOImpl orderDAO =new OrderDAOImpl();
    private final UserDAOImpl userDAO =new UserDAOImpl();


    public DAOFactory() {
    }

    public static DAOFactory getInstance() {
        return instance;
    }

    public CarDAOImpl getCarDAO() {
        return carDAO;
    }

    public AccidentHistoryDAOImpl getAccidentHistoryDAO() {
        return accidentHistoryDAO;
    }

    public DamageAssesmentDAOImpl getDamageAssesmentDAO() {
        return damageAssesmentDAO;
    }

    public OrderDAOImpl getOrderDAO() {
        return orderDAO;
    }

    public UserDAOImpl getUserDAO() {
        return userDAO;
    }
}
