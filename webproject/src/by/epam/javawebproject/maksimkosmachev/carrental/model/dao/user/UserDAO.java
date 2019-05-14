package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.user;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.DAOInterface;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.DAOException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.SuchUserExistsException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.SuchUserNotExistException;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Entity;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.User;

public interface UserDAO extends DAOInterface {
    void registration(Entity entity) throws SuchUserExistsException, DAOException;
    User signIn(String login, String password) throws SuchUserNotExistException, DAOException;
}
