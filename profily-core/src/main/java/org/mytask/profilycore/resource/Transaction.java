package org.mytask.profilycore.resource;

import org.mytask.profilycore.entity.Payment;

import java.util.Date;

public class Transaction {
    Date date;
    Long amountCents;
    String description;

    public Transaction(Date date, Long amountCents, String description) {
        this.date = date;
        this.amountCents = amountCents;
        this.description = description;
    }

    public static Transaction fromPayment(Payment payment) {
        return new Transaction(payment.getDate(), payment.getAmountCents(), payment.getDescription());
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
}
