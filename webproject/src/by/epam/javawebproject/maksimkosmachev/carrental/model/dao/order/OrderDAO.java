package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.order;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.DAOInterface;
import by.epam.javawebproject.maksimkosmachev.carrental.model.exception.SuchOrderNotExistsException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Car;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Order;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.User;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.Condition;

public interface OrderDAO extends DAOInterface {
    Order findByUser(User user) throws SuchOrderNotExistsException;
    Order findByCar(Car car) throws SuchOrderNotExistsException;
    void processOrder(boolean decision, Order order);
    void setCarCondition(Condition condition, Order order);
    void justifyRefuse(String refuse, Order order);
    void updateTotalSum(Double totalSum, Order order);
}
