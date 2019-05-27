package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import java.util.Objects;

public class DamageAssessment extends Entity {
    private double carHood;
    private double carBoot;
    private double frontDoor;
    private double rearDoor;
    private double fender;
    private Order order;

    public DamageAssessment(double carHood, double carBoot, double frontDoor,
                            double rearDoor, double fender) {
        this.carHood = carHood;
        this.carBoot = carBoot;
        this.frontDoor = frontDoor;
        this.rearDoor = rearDoor;
        this.fender = fender;
        this.order =new Order();
    }

    public DamageAssessment() {
        this.carHood = -1;
        this.carBoot = -1;
        this.frontDoor = -1;
        this.rearDoor = -1;
        this.fender = -1;
        this.order =new Order();
    }



    public double getCarHood() {
        return carHood;
    }

    public void setCarHood(double carHood) {
        this.carHood = carHood;
    }

    public double getCarBoot() {
        return carBoot;
    }

    public void setCarBoot(double carBoot) {
        this.carBoot = carBoot;
    }

    public double getFrontDoor() {
        return frontDoor;
    }

    public void setFrontDoor(double frontDoor) {
        this.frontDoor = frontDoor;
    }

    public double getRearDoor() {
        return rearDoor;
    }

    public void setRearDoor(double rearDoor) {
        this.rearDoor = rearDoor;
    }

    public double getFender() {
        return fender;
    }

    public void setFender(double fender) {
        this.fender = fender;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public int getOrderId() {
        return order.getId();
    }

    public void setOrderId(int id) {
        order.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DamageAssessment that = (DamageAssessment) o;
        return Double.compare(that.carHood, carHood) == 0 &&
                Double.compare(that.carBoot, carBoot) == 0 &&
                Double.compare(that.frontDoor, frontDoor) == 0 &&
                Double.compare(that.rearDoor, rearDoor) == 0 &&
                Double.compare(that.fender, fender) == 0 &&
                Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(carHood, carBoot, frontDoor, rearDoor, fender, order);
    }

    @Override
    public String toString() {
        return " DamageAssessment{" +
                "damageId=" + getId() +
                ", carHood=" + carHood +
                ", carBoot=" + carBoot +
                ", frontDoor=" + frontDoor +
                ", rearDoor=" + rearDoor +
                ", fender=" + fender + ", orderId "+order.getId()+
                '}';
    }
}
