package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.BodyType;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.Manufacturer;
import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.TransmissionType;

import java.util.Objects;

public class Car extends Entity {
    private Manufacturer manufacturer;
    private String model;
    private int yearIssue;
    private BodyType bodyType;
    private TransmissionType transmissionType;
    private double engineValue;
    private double rentPrice;


    public Car(int id, Manufacturer manufacturer, String model, int yearIssue,
               BodyType bodyType, TransmissionType transmissionType, double engineValue,
               double rentPrice) {
        super(id);
        this.manufacturer = manufacturer;
        this.model = model;
        this.yearIssue = yearIssue;
        this.bodyType = bodyType;
        this.transmissionType = transmissionType;
        this.engineValue = engineValue;
        this.rentPrice = rentPrice;
    }

    public Car() {
    }

    public Manufacturer getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearIssue() {
        return yearIssue;
    }

    public void setYearIssue(int yearIssue) {
        this.yearIssue = yearIssue;
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void setBodyType(BodyType bodyType) {
        this.bodyType = bodyType;
    }

    public TransmissionType getTransmissionType() {
        return transmissionType;
    }

    public void setTransmissionType(TransmissionType transmissionType) {
        this.transmissionType = transmissionType;
    }

    public double getEngineValue() {
        return engineValue;
    }

    public void setEngineValue(double engineValue) {
        this.engineValue = engineValue;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
        return yearIssue == car.yearIssue &&
                Double.compare(car.engineValue, engineValue) == 0 &&
                Double.compare(car.rentPrice, rentPrice) == 0 &&
                manufacturer == car.manufacturer &&
                Objects.equals(model, car.model) &&
                bodyType == car.bodyType &&
                transmissionType == car.transmissionType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturer, model, yearIssue, bodyType, transmissionType, engineValue, rentPrice);
    }

    @Override
    public String toString() {
        return super.toString()+"Car{" +
                "manufacturer=" + manufacturer +
                ", model='" + model + '\'' +
                ", yearIssue=" + yearIssue +
                ", bodyType=" + bodyType +
                ", transmissionType=" + transmissionType +
                ", engineValue=" + engineValue +
                ", rentPrice=" + rentPrice +
                '}';
    }
}
