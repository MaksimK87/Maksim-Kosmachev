package by.epam.javawebproject.maksimkosmachev.carrental.model.dao.car;

import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.Car;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.Manufacturer;
import by.epam.javawebproject.maksimkosmachev.carrental.model.dao.DAOInterface;

import java.util.List;

public interface CarDAO extends DAOInterface {
    List<Car> getCarByManufacturer(Manufacturer manufacturer);
    List<Car> getCarByYear(int yearFrom, int yearTo);
}
