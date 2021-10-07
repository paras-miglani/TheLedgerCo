package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;

import java.util.Objects;

public class PaymentRequest {
    private Double lumpSumAmount;
    private Integer emiNumber;
    private Bank bank;
    private Customer borrower;

    public Double getLumpSumAmount() {
        return lumpSumAmount;
    }

    public void setLumpSumAmount(Double lumpSumAmount) {
        this.lumpSumAmount = lumpSumAmount;
    }

    public Integer getEmiNumber() {
        return emiNumber;
    }

    public void setEmiNumber(Integer emiNumber) {
        this.emiNumber = emiNumber;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public void setBorrower(Customer borrower) {
        this.borrower = borrower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PaymentRequest)) return false;
        PaymentRequest that = (PaymentRequest) o;
        return Objects.equals(getLumpSumAmount(), that.getLumpSumAmount()) &&
                Objects.equals(getEmiNumber(), that.getEmiNumber()) &&
                Objects.equals(getBank().getName(), that.getBank().getName()) &&
                Objects.equals(getBorrower().getName(), that.getBorrower().getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLumpSumAmount(), getEmiNumber(), getBank().getName(), getBorrower().getName());
    }
}
