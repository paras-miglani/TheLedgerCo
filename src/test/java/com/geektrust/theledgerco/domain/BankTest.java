package com.geektrust.theledgerco.domain;

import com.geektrust.theledgerco.exceptions.CustomerAlreadyExistException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class BankTest {
    Bank bank;

    @Test
    void shouldAddCustomer() throws CustomerAlreadyExistException {
        bank = new Bank("Bank Name");
        Customer customer = new Customer("borrower name");
        Account account = bank.addCustomer(customer);
        Assertions.assertNotNull(account);
    }

    @Test
    void shouldThrowExceptionIfCustomerAlreadyExists() throws CustomerAlreadyExistException {
        bank = new Bank("Bank Name");
        Customer customer = new Customer("borrower name");
        bank.addCustomer(customer);
        CustomerAlreadyExistException customerAlreadyExistException = assertThrows(CustomerAlreadyExistException.class,
                () -> bank.addCustomer(customer));
        assertEquals("Customer already existsCustomer{name='borrower name'}",
                customerAlreadyExistException.getMessage());
    }

    @Test
    void shouldFindCustomer() throws CustomerAlreadyExistException, CustomerNotFoundException {
        bank = new Bank("Bank Name");
        Customer customer = new Customer("borrower name");
        Account expectedAccount = bank.addCustomer(customer);
        Account actualAccount = bank.findCustomerAccount(customer);
        Assertions.assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void shouldThrowExceptionIfCustomerNotFound() {
        bank = new Bank("Bank Name");
        Customer customer = new Customer("borrower name");
        CustomerNotFoundException customerNotFoundException = assertThrows(CustomerNotFoundException.class,
                () -> bank.findCustomerAccount(customer));
        assertEquals("Customer not found Customer{name='borrower name'}",
                customerNotFoundException.getMessage());
    }
}