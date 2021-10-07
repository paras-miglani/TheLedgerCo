package com.geektrust.theledgerco.domain;

import java.util.Objects;

public class Payment {
    private final Double lumpSumAmount;
    private final Integer emiNumber;

    public Payment(Double lumpSumAmount, Integer emiNumber) {
        this.lumpSumAmount = lumpSumAmount;
        this.emiNumber = emiNumber;
    }

    public Double getLumpSumAmount() {
        return lumpSumAmount;
    }

    public Integer getEmiNumber() {
        return emiNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Payment)) return false;
        Payment payment = (Payment) o;
        return Objects.equals(getLumpSumAmount(), payment.getLumpSumAmount()) &&
                Objects.equals(getEmiNumber(), payment.getEmiNumber());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLumpSumAmount(), getEmiNumber());
    }
}
