package org.mytask.profilycore.resource;

import java.util.Date;
import java.util.List;

public class Customer {
    private Long customerId;
    private List<Classification> classifications;
    private Date dateOfBalance;
    private Long balance;
    private Date monthDate;
    private List<Transaction> transactionsInMonth;


    public Customer(Long customerId, List<Classification> classifications, Date dateOfBalance, Long balance,
                    Date monthDate, List<Transaction> transactionsInMonth) {
        this.customerId = customerId;
        this.classifications = classifications;
        this.dateOfBalance = dateOfBalance;
        this.balance = balance;
        this.monthDate = monthDate;
        this.transactionsInMonth = transactionsInMonth;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Classification> getClassifications() {
        return classifications;
    }

    public void setClassifications(List<Classification> classifications) {
        this.classifications = classifications;
    }

    public Date getDateOfBalance() {
        return dateOfBalance;
    }

    public void setDateOfBalance(Date dateOfBalance) {
        this.dateOfBalance = dateOfBalance;
    }

    public Long getBalance() {
        return balance;
    }

    public void setBalance(Long balance) {
        this.balance = balance;
    }

    public Date getMonthDate() {
        return monthDate;
    }

    public void setMonthDate(Date monthDate) {
        this.monthDate = monthDate;
    }

    public List<Transaction> getTransactionsInMonth() {
        return transactionsInMonth;
    }

    public void setTransactionsInMonth(List<Transaction> transactionsInMonth) {
        this.transactionsInMonth = transactionsInMonth;
    }
}
