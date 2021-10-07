package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;

import java.util.Objects;

public class BalanceResponse {
    private Bank bank;
    private Customer borrower;
    private Double amountPaid;
    private Double noOfEmisLeft;

    public BalanceResponse(Bank bank, Customer borrower, Double amountPaid, Double noOfEmisLeft) {
        this.bank = bank;
        this.borrower = borrower;
        this.amountPaid = amountPaid;
        this.noOfEmisLeft = noOfEmisLeft;
    }

    public String getBankName() {
        return bank.getName();
    }

    public String getBorrowerName() {
        return borrower.getName();
    }


    public String getAmountPaid() {
        return String.format("%.0f", Math.ceil(amountPaid));
    }

    public String getNoOfEmisLeft() {
        return String.format("%.0f", Math.ceil(noOfEmisLeft));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BalanceResponse)) return false;
        BalanceResponse that = (BalanceResponse) o;
        return Objects.equals(bank.getName(), that.bank.getName()) &&
                Objects.equals(borrower.getName(), that.borrower.getName()) &&
                Objects.equals(getAmountPaid(), that.getAmountPaid()) &&
                Objects.equals(getNoOfEmisLeft(), that.getNoOfEmisLeft());
    }

    @Override
    public int hashCode() {
        return Objects.hash(bank.getName(), borrower.getName(), getAmountPaid(), getNoOfEmisLeft());
    }
}
