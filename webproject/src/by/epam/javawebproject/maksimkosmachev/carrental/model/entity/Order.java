package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.Condition;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends Entity {
    private int rentTerm;
    private double totalSum;
    private LocalDate rentFromDate;
    private boolean isPaid;
    private Condition conditionAfterRefund;
    private LocalDate refundDate;
    private boolean acceptOrder;
    private String refusalReason;
    private Car car;
    private User user;
//    private AccidentHistoryDAO accidentHistory;
//    private DamageAssessment damageAssessment;

    public Order(int rentTerm, double totalSum, LocalDate rentFromDate, boolean isPaid,
                 Condition conditionAfterRefund, LocalDate refundDate,
                 boolean acceptOrder, String refusalReason) {
        //super(id);
        this.rentTerm = rentTerm;
        this.totalSum = totalSum;
        this.rentFromDate = rentFromDate;
        this.isPaid = isPaid;
        this.conditionAfterRefund = conditionAfterRefund;
        this.refundDate = refundDate;
        this.acceptOrder = acceptOrder;
        this.refusalReason = refusalReason;
    }

    public Order() {
    car=new Car();
    user=new User();
    }


    public int getRentTerm() {
        return rentTerm;
    }

    public void setRentTerm(int rentTerm) {
        this.rentTerm = rentTerm;
    }

    public double getTotalSum() {
        return totalSum;
    }

    public void setTotalSum(double totalSum) {
        this.totalSum = totalSum;
    }

    public LocalDate getRentFromDate() {
        return rentFromDate;
    }

    public void setRentFromDate(LocalDate rentFromDate) {
        this.rentFromDate = rentFromDate;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Condition getConditionAfterRefund() {
        return conditionAfterRefund;
    }

    public void setConditionAfterRefund(Condition conditionAfterRefund) {
        this.conditionAfterRefund = conditionAfterRefund;
    }

    public LocalDate getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDate refundDate) {
        this.refundDate = refundDate;
    }

    public boolean getAcceptOrder() {
        return acceptOrder;
    }

    public void setAcceptOrder(boolean acceptOrder) {
        this.acceptOrder = acceptOrder;
    }

    public String getRefusalReason() {
        return refusalReason;
    }

    public void setRefusalReason(String refusalReason) {
        this.refusalReason = refusalReason;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public int getCarId() {
        return car.getId();
    }

    public void setCarId(int id) {
        car.setId(id);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserId(int id) {
        this.user.setId(id);
    }

    public int getUserId() {
        return user.getId();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return rentTerm == order.rentTerm &&
                Double.compare(order.totalSum, totalSum) == 0 &&
                isPaid == order.isPaid &&
                acceptOrder == order.acceptOrder &&
                Objects.equals(rentFromDate, order.rentFromDate) &&
                conditionAfterRefund == order.conditionAfterRefund &&
                Objects.equals(refundDate, order.refundDate) &&
                Objects.equals(refusalReason, order.refusalReason) &&
                Objects.equals(car, order.car) &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rentTerm, totalSum, rentFromDate, isPaid, conditionAfterRefund, refundDate, acceptOrder, refusalReason, car, user);
    }

    @Override
    public String toString() {
        return "Order{ " + getId() +
                " rentTerm=" + rentTerm +
                ", totalSum=" + totalSum +
                ", rentFromDate=" + rentFromDate +
                ", isPaid=" + isPaid +
                ", conditionAfterRefund=" + conditionAfterRefund +
                ", refundDate=" + refundDate +
                ", acceptOrder=" + acceptOrder +
                ", refusalReason='" + refusalReason + '\'' +
                ", car=" + car +
                ", user=" + user +
                '}';
    }
}
