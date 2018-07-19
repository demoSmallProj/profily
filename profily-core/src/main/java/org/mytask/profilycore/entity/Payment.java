package org.mytask.profilycore.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long customerId;
    private Date date;
    private Long amountCents;
    private String description;

    public Payment(Long customerId, Date date, Long amountCents, String description) {
        this.customerId = customerId;
        this.date = date;
        this.amountCents = amountCents;
        this.description = description;
    }

    public Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getAmountCents() {
        return amountCents;
    }

    public void setAmountCents(Long amountCents) {
        this.amountCents = amountCents;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Payment payment = (Payment) o;
        return Objects.equals(id, payment.id) &&
                Objects.equals(customerId, payment.customerId) &&
                Objects.equals(date, payment.date) &&
                Objects.equals(amountCents, payment.amountCents) &&
                Objects.equals(description, payment.description);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, customerId, date, amountCents, description);
    }
}
