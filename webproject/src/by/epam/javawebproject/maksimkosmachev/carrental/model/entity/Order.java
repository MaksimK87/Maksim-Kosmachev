package by.epam.javawebproject.maksimkosmachev.carrental.model.entity;

import by.epam.javawebproject.maksimkosmachev.carrental.model.entity.enumpackage.Condition;

import java.time.LocalDateTime;
import java.util.Objects;

public class Order extends Entity {
    private int rentTerm;
    private double totalSum;
    private LocalDateTime rentFromDate;
    private boolean isPaid;
    private Condition conditionAfterRefund;
    private LocalDateTime refundDate;
    private boolean acceptOrder;
    private String refusalReason;

    public Order(int id, int rentTerm, double totalSum, LocalDateTime rentFromDate, boolean isPaid,
                 Condition conditionAfterRefund, LocalDateTime refundDate,
                 boolean acceptOrder, String refusalReason) {
        super(id);
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

    public LocalDateTime getRentFromDate() {
        return rentFromDate;
    }

    public void setRentFromDate(LocalDateTime rentFromDate) {
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

    public LocalDateTime getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(LocalDateTime refundDate) {
        this.refundDate = refundDate;
    }

    public boolean isAcceptOrder() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getId() == order.getId() &&
                rentTerm == order.rentTerm &&
                Double.compare(order.totalSum, totalSum) == 0 &&
                isPaid == order.isPaid &&
                acceptOrder == order.acceptOrder &&
                rentFromDate.equals(order.rentFromDate) &&
                conditionAfterRefund == order.conditionAfterRefund &&
                refundDate.equals(order.refundDate) &&
                Objects.equals(refusalReason, order.refusalReason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), rentTerm, totalSum, rentFromDate, isPaid, conditionAfterRefund, refundDate, acceptOrder, refusalReason);
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + getId() +
                ", rentTerm=" + rentTerm +
                ", totalSum=" + totalSum +
                ", rentFromDate=" + rentFromDate +
                ", isPaid=" + isPaid +
                ", conditionAfterRefund=" + conditionAfterRefund +
                ", refundDate=" + refundDate +
                ", acceptOrder=" + acceptOrder +
                ", refusalReason='" + refusalReason + '\'' +
                '}';
    }
}
