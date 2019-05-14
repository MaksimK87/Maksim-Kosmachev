package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import java.util.Objects;

public class DamageAssessment extends Entity {
    private double carHood;
    private double carBoot;
    private double frontDoor;
    private double rearDoor;
    private double fender;

    public DamageAssessment(int id, double carHood, double carBoot, double frontDoor,
                            double rearDoor, double fender) {
        super(id);
        this.carHood = carHood;
        this.carBoot = carBoot;
        this.frontDoor = frontDoor;
        this.rearDoor = rearDoor;
        this.fender = fender;
    }
    public DamageAssessment(){

    }

    public DamageAssessment(DamageAssessment damageAssessment){
        this.setId(damageAssessment.getId());
        this.carHood = damageAssessment.carHood;
        this.carBoot = damageAssessment.carBoot;
        this.frontDoor = damageAssessment.frontDoor;
        this.rearDoor = damageAssessment.rearDoor;
        this.fender = damageAssessment.fender;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DamageAssessment that = (DamageAssessment) o;
        return getId() == that.getId() &&
                Double.compare(that.carHood, carHood) == 0 &&
                Double.compare(that.carBoot, carBoot) == 0 &&
                Double.compare(that.frontDoor, frontDoor) == 0 &&
                Double.compare(that.rearDoor, rearDoor) == 0 &&
                Double.compare(that.fender, fender) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), carHood, carBoot, frontDoor, rearDoor, fender);
    }

    @Override
    public String toString() {
        return "DamageAssessment{" +
                "damageId=" + getId() +
                ", carHood=" + carHood +
                ", carBoot=" + carBoot +
                ", frontDoor=" + frontDoor +
                ", rearDoor=" + rearDoor +
                ", fender=" + fender +
                '}';
    }
}
