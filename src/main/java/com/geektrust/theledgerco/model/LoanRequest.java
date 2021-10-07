package com.geektrust.theledgerco.model;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import lombok.Data;

@Data
public class LoanRequest {
    private Bank bank;
    private Customer borrower;
    private Double principal;
    private Integer noOfYears;
    private Double rateOfInterest;

    public Double calculateAmount() {
        return ((principal * noOfYears * rateOfInterest) / 100.0) + principal;
    }

    public Integer calculateNoOfEmi() {
        return noOfYears * 12;
    }
}
