package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class AccidentHistory extends Entity {
    private double damageCost;
    private boolean guilt;
    private LocalDate accidentDate;
    private Order order;

    public AccidentHistory(double damageCost, boolean guilt, LocalDate accidentDate) {
        this.damageCost = damageCost;
        this.guilt = guilt;
        this.accidentDate = accidentDate;
    }

    public AccidentHistory() {

    }

    public double getDamageCost() {
        return damageCost;
    }

    public void setDamageCost(double damageCost) {
        this.damageCost = damageCost;
    }

    public boolean getGuilt() {

        return guilt;
    }

    public void setGuilt(boolean guilt) {
        this.guilt = guilt;
    }

    public LocalDate getAccidentDate() {
        return accidentDate;
    }

    public void setAccidentDate(LocalDate accidentDate) {
        this.accidentDate = accidentDate;
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
        AccidentHistory that = (AccidentHistory) o;
        return Double.compare(that.damageCost, damageCost) == 0 &&
                guilt == that.guilt &&
                accidentDate.equals(that.accidentDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(damageCost, guilt, accidentDate);
    }

    @Override
    public String toString() {
        return super.getId() + " AccidentHistoryDAO{" +
                "damageCost=" + damageCost +
                ", guilt=" + guilt +
                ", accidentDate=" + accidentDate +
                '}';
    }
}
