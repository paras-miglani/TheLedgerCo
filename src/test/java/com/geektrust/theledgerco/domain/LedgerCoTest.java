package com.geektrust.theledgerco.domain;

import com.geektrust.theledgerco.exceptions.BankNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class LedgerCoTest {
    @InjectMocks
    LedgerCo ledgerCo;

    @Test
    void shouldAddBank() {
        Bank bank = new Bank("bankName");
        Bank actualBank = ledgerCo.addBank(bank);
        assertNotNull(actualBank);
    }

    @Test
    void shouldFindBank() throws BankNotFoundException {
        Bank bank = new Bank("bankName");
        Bank expectedBank = ledgerCo.addBank(bank);
        Bank actualBank = ledgerCo.findBank(bank);
        assertEquals(expectedBank, actualBank);
    }

    @Test
    void shouldThrowExceptionIfBankNotFound() {
        Bank bank = new Bank("bankName");
        BankNotFoundException bankNotFoundException = assertThrows(BankNotFoundException.class, () -> ledgerCo.findBank(bank));
        assertEquals("Bank Not found Bank(name=bankName)", bankNotFoundException.getMessage());
    }
}