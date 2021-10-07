package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
@AllArgsConstructor
public class BalanceResponse {
    private final Bank bank;
    private final Customer borrower;
    private final Double amountPaid;
    private final Double noOfEmisLeft;

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
}
