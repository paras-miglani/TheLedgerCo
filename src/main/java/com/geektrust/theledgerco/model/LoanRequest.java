package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;

import java.util.Objects;

public class LoanRequest {
    private Bank bank;
    private Customer borrower;
    private Double principal;
    private Integer noOfYears;
    private Double rateOfInterest;

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public void setBorrower(Customer borrower) {
        this.borrower = borrower;
    }

    public void setPrincipal(Double principal) {
        this.principal = principal;
    }

    public void setNoOfYears(Integer noOfYears) {
        this.noOfYears = noOfYears;
    }

    public void setRateOfInterest(Double rateOfInterest) {
        this.rateOfInterest = rateOfInterest;
    }

    public Bank getBank() {
        return bank;
    }

    public Customer getBorrower() {
        return borrower;
    }

    public Double getPrincipal() {
        return principal;
    }

    public Integer getNoOfYears() {
        return noOfYears;
    }

    public Double getRateOfInterest() {
        return rateOfInterest;
    }

    public Double calculateAmount() {
        return ((principal * noOfYears * rateOfInterest) / 100.0) + principal;
    }

    public Integer calculateNoOfEmi() {
        return noOfYears * 12;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LoanRequest)) return false;
        LoanRequest that = (LoanRequest) o;
        return Objects.equals(getBank().getName(), that.getBank().getName()) &&
                Objects.equals(getBorrower().getName(), that.getBorrower().getName()) &&
                Objects.equals(getPrincipal(), that.getPrincipal()) &&
                Objects.equals(getNoOfYears(), that.getNoOfYears()) &&
                Objects.equals(getRateOfInterest(), that.getRateOfInterest());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBank().getName(), getBorrower().getName(), getPrincipal(), getNoOfYears(),
                getRateOfInterest());
    }
}
