package com.geektrust.theledgerco.domain;

import com.geektrust.theledgerco.exceptions.CustomerAlreadyExistException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Bank {
    private final String name;
    private final Map<Customer, Account> customers;

    public Bank(String name) {
        this.name = name;
        this.customers = new HashMap<>();
    }

    public Account addCustomer(Customer customer) throws CustomerAlreadyExistException {
        if (Objects.nonNull(customers.get(customer))) {
            throw new CustomerAlreadyExistException("Customer already exists" + customer);
        }
        Account account = new Account();
        customers.put(customer, account);
        return account;
    }

    public Account findCustomerAccount(Customer customer) throws CustomerNotFoundException {
        return Optional.ofNullable(customers.get(customer))
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found " + customer));
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bank)) return false;
        Bank bank = (Bank) o;
        return Objects.equals(name, bank.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name='" + name + '\'' +
                '}';
    }
}
