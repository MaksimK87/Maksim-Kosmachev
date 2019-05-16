package by.epam.javawebproject.maksimkosmachev.carrental.model.dao;

import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.exception.*;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Entity;

import java.util.List;

public interface DAOInterface<T extends Entity> {
    List<T> findAll() throws EmptyResultSetException, DAOException;

    T findEntityById(int id) throws SuchUserExistsException, SuchUserNotExistException, SuchOrderNotExistsException;

    boolean delete(T entity);

    boolean insert(T entity);

    boolean update(T entity);


}
