package com.geektrust.theledgerco.domain;

import java.util.ArrayList;
import java.util.List;

public class Account {
    private Double loanAmount;
    private Integer noOfEmi;
    private List<Payment> lumSumPayments;

    public Account() {
        lumSumPayments = new ArrayList<>();
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public void setNoOfEmi(Integer noOfEmi) {
        this.noOfEmi = noOfEmi;
    }

    public void addPayment(Payment payment) {
        lumSumPayments.add(payment);
    }

    public Double calculateTotalAmountPaid(Integer emiNumber) {
        double emiAmount = getEmiAmount();
        Double lumpSumAmount = getLumpSumAmount(emiNumber);
        return emiAmount * emiNumber + lumpSumAmount;
    }

    private Double getLumpSumAmount(Integer emiNumber) {
        return lumSumPayments.stream()
                .filter(e -> e.getEmiNumber() <= emiNumber)
                .map(Payment::getLumpSumAmount).reduce(Double::sum).orElse(0.0);
    }

    private double getEmiAmount() {
        return Math.ceil(loanAmount / noOfEmi);
    }

    public Double noOfEmisLeft(Integer emiNumber) {
        Double totalAmountPaid = calculateTotalAmountPaid(emiNumber);
        double amountPending = loanAmount - totalAmountPaid;
        double noOfEMis = amountPending / getEmiAmount();
        return noOfEMis < 0.0 ? 0d : noOfEMis;
    }
}
