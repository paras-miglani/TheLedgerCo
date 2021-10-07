package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import lombok.Data;

@Data
public class PaymentRequest {
    private Double lumpSumAmount;
    private Integer emiNumber;
    private Bank bank;
    private Customer borrower;
}
