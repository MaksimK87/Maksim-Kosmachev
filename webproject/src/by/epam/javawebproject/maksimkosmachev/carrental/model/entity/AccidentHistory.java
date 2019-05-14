package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import java.time.LocalDate;
import java.util.Objects;

public class AccidentHistory extends Entity {
    private double damageCost;
    private boolean guilt;
    private LocalDate accidentDate;

    public AccidentHistory(int id,double damageCost, boolean guilt, LocalDate accidentDate) {
        super(id);
        this.damageCost = damageCost;
        this.guilt = guilt;
        this.accidentDate = accidentDate;
    }
    public AccidentHistory(){

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
        return "AccidentHistory{" +
                "damageCost=" + damageCost +
                ", guilt=" + guilt +
                ", accidentDate=" + accidentDate +
                '}';
    }
}
