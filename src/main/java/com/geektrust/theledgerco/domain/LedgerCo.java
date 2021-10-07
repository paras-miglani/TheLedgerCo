package com.geektrust.theledgerco.domain;

import com.geektrust.theledgerco.exceptions.BankNotFoundException;

import java.util.HashSet;
import java.util.Set;

public class LedgerCo {
    private final Set<Bank> banks;

    public LedgerCo() {
        this.banks = new HashSet<>();
    }

    public Bank addBank(Bank bank) {
        banks.add(bank);
        return bank;
    }

    public Bank findBank(Bank bank) throws BankNotFoundException {
        return banks.stream()
                .filter(b -> b.equals(bank))
                .findFirst()
                .orElseThrow(() -> new BankNotFoundException("Bank Not found " + bank));
    }
}
