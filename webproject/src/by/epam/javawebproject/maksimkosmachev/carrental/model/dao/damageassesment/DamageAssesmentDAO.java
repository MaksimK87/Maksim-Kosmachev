package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.damageassesment;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.DAOInterface;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.DamageAssessment;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Order;

public interface DamageAssesmentDAO extends DAOInterface {
    DamageAssessment getDamageByOrderId(Order order);
}
