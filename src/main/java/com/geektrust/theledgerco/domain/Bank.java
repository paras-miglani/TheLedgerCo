package com.geektrust.theledgerco.domain;

import com.geektrust.theledgerco.exceptions.CustomerAlreadyExistException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@EqualsAndHashCode
@ToString
public class Bank {
    @Getter
    private final String name;

    @EqualsAndHashCode.Exclude
    @ToString.Exclude
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
}
