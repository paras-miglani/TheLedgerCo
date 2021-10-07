package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import lombok.Data;

@Data
public class BalanceRequest {
    private Bank bank;
    private Customer borrower;
    private Integer emiNumber;
}
