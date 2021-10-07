package com.geektrust.theledgerco.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {
    @Test
    void validateEqualsAndHashCode() {
        Customer customer = new Customer("customer");
        Customer customer2 = new Customer("customer");
        assertTrue(customer.equals(customer2));
        assertTrue(customer.hashCode() == customer2.hashCode());
    }

    @Test
    void validateEqualsAndHashCodeWithInEqualObjects() {
        Customer customer = new Customer("customer");
        Customer customer2 = new Customer("customer1");
        assertFalse(customer.equals(customer2));
        assertFalse(customer.hashCode() == customer2.hashCode());
    }
}