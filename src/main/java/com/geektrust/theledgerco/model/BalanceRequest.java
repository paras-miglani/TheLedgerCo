package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;

import java.util.Objects;

public class BalanceRequest {
    private Bank bank;
    private Customer borrower;
    private Integer emiNumber;

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

    public Integer getEmiNumber() {
        return emiNumber;
    }

    public void setEmiNumber(Integer emiNumber) {
        this.emiNumber = emiNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceRequest)) return false;
        BalanceRequest that = (BalanceRequest) o;
        return Objects.equals(getBank().getName(), that.getBank().getName()) &&
                Objects.equals(getBorrower().getName(), that.getBorrower().getName()) &&
                Objects.equals(getEmiNumber(), that.getEmiNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBank().getName(), getBorrower().getName(), getEmiNumber());
    }
}
