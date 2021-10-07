package com.geektrust.theledgerco.commands;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.BankNotFoundException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;
import com.geektrust.theledgerco.exceptions.InvalidBalanceCommandException;
import com.geektrust.theledgerco.io.IO;
import com.geektrust.theledgerco.model.BalanceRequest;
import com.geektrust.theledgerco.model.BalanceResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BalanceCommandTest {
    @Mock
    private LedgerService ledgerService;
    @Mock
    private IO io;
    @InjectMocks
    private BalanceCommand command;

    @Test
    void shouldThrowExceptionIfParametersLengthIsNotValid() {
        InvalidBalanceCommandException balanceCommandException = assertThrows(InvalidBalanceCommandException.class,
                () -> command.execute(Collections.emptyList()));
        assertEquals("Parameter must be of size 3", balanceCommandException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfEmiNumberIsNotValid() {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        String emi_number = "emi Number";
        params.add(emi_number);
        InvalidBalanceCommandException balanceCommandException = assertThrows(InvalidBalanceCommandException.class,
                () -> command.execute(params));
        assertEquals("Emi number is not valid " + emi_number, balanceCommandException.getMessage());
    }

    @Test
    void shouldCallGetBalanceOnValidRequest() throws BankNotFoundException,
            CustomerNotFoundException, InvalidBalanceCommandException {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        params.add("2");

        BalanceRequest balanceRequest = createBalanceRequest(params);
        when(ledgerService.getBalance(balanceRequest)).thenReturn(new BalanceResponse(new Bank("bank"),
                new Customer("customer"), 10000d, 5d));

        command.execute(params);
        verify(ledgerService).getBalance(balanceRequest);
        verify(io).printLine("bank customer 10000 5");
    }

    private BalanceRequest createBalanceRequest(List<String> params) throws InvalidBalanceCommandException {
        BalanceRequest balanceRequest = new BalanceRequest();
        String bankName = params.get(0);
        balanceRequest.setBank(new Bank(bankName));

        String borrowerName = params.get(1);
        balanceRequest.setBorrower(new Customer(borrowerName));

        try {
            Integer emiNumber = Integer.valueOf(params.get(2));
            balanceRequest.setEmiNumber(emiNumber);
        } catch (NumberFormatException ex) {
            throw new InvalidBalanceCommandException("Emi number is not valid " + params.get(2), ex);
        }
        return balanceRequest;
    }
}