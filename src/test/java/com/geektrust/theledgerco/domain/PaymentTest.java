package com.geektrust.theledgerco.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {
    @Test
    void validateEqualsAndHashCode() {
        Payment payment = new Payment(1000d, 1);
        Payment payment2 = new Payment(1000d, 1);
        assertEquals(payment2, payment);
        assertEquals(payment2.hashCode(), payment.hashCode());
    }

    @Test
    void validateEqualsAndHashCodeWithInEqualObjects() {
        Payment payment = new Payment(1000d, 1);
        Payment payment2 = new Payment(1000d, 2);
        assertNotEquals(payment2, payment);
        assertNotEquals(payment.hashCode(), payment2.hashCode());
    }
}