package com.geektrust.theledgerco.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AccountTest {
    Account account;

    @BeforeEach
    void setUp() {
        account = new Account();
    }

    @Test
    void shouldCalculateTotalAmountWithoutLumpSumPayment() {
        account.setLoanAmount(10000d);
        account.setNoOfEmi(10);

        Double actualTotalAmountPaid = account.calculateTotalAmountPaid(5);
        Assertions.assertEquals(5000d, actualTotalAmountPaid.doubleValue());
    }

    @Test
    void shouldCalculateTotalAmountWithLumpSumPayment() {
        account.setLoanAmount(10000d);
        account.setNoOfEmi(10);
        account.addPayment(new Payment(5000d, 5));

        Double actualTotalAmountPaid = account.calculateTotalAmountPaid(5);
        Assertions.assertEquals(10000d, actualTotalAmountPaid.doubleValue());
    }

    @Test
    void shouldCalculateTotalAmount() {
        account.setLoanAmount(10000d);
        account.setNoOfEmi(10);
        account.addPayment(new Payment(5000d, 3));

        Double actualTotalAmountPaid = account.calculateTotalAmountPaid(2);
        Assertions.assertEquals(2000d, actualTotalAmountPaid.doubleValue());
    }

    @Test
    void shouldCalculateNumberOfEmisLeftWithLumpSumPayment() {
        account.setLoanAmount(10000d);
        account.setNoOfEmi(10);
        account.addPayment(new Payment(5000d, 5));

        Double noOfEmisLeft = account.noOfEmisLeft(5);
        Assertions.assertEquals(0, noOfEmisLeft.doubleValue());
    }

    @Test
    void shouldReturnZeroEmisWhenPaymentAlreadyPaidLeftWithLumpSumPayment() {
        account.setLoanAmount(10000d);
        account.setNoOfEmi(10);
        account.addPayment(new Payment(5000d, 5));

        Double noOfEmisLeft = account.noOfEmisLeft(6);
        Assertions.assertEquals(0, noOfEmisLeft.doubleValue());
    }

    @Test
    void shouldCalculateNumberOfEmisLeftWithoutLumpSumPayment() {
        account.setLoanAmount(10000d);
        account.setNoOfEmi(10);

        Double noOfEmisLeft = account.noOfEmisLeft(5);
        Assertions.assertEquals(5, noOfEmisLeft.doubleValue());
    }
}