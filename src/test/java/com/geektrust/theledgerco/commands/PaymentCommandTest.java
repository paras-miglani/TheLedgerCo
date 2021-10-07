package com.geektrust.theledgerco.commands;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.ClientException;
import com.geektrust.theledgerco.exceptions.InvalidPaymentCommandException;
import com.geektrust.theledgerco.model.PaymentRequest;
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

@ExtendWith(MockitoExtension.class)
class PaymentCommandTest {
    @Mock
    LedgerService ledgerService;
    @InjectMocks
    PaymentCommand command;

    @Test
    void shouldThrowExceptionIfParametersLengthIsNotValid() {
        InvalidPaymentCommandException paymentCommandException = assertThrows(InvalidPaymentCommandException.class,
                () -> command.execute(Collections.emptyList()));
        assertEquals("Parameter must be of size 4", paymentCommandException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfLumpSumAmountIsNotValid() {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        String lumpSumAmount = "lumpSumAmount";
        params.add(lumpSumAmount);
        params.add("2");
        InvalidPaymentCommandException paymentCommandException = assertThrows(InvalidPaymentCommandException.class,
                () -> command.execute(params));
        assertEquals("lumpSumAmount is not valid " + lumpSumAmount, paymentCommandException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfEmiNumberIsNotValid() {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        params.add("1000");
        String emiNumber = "emiNumber";
        params.add(emiNumber);
        InvalidPaymentCommandException paymentCommandException = assertThrows(InvalidPaymentCommandException.class,
                () -> command.execute(params));
        assertEquals("Emi number is not valid " + emiNumber, paymentCommandException.getMessage());
    }

    @Test
    void shouldAddPaymentOnValidRequest() throws ClientException {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        params.add("1000");
        params.add("2");
        PaymentRequest paymentRequest = createPaymentRequest(params);

        command.execute(params);
        verify(ledgerService).addPayment(paymentRequest);
    }

    private PaymentRequest createPaymentRequest(List<String> params) throws InvalidPaymentCommandException {
        PaymentRequest paymentRequest = new PaymentRequest();
        String bankName = params.get(0);
        paymentRequest.setBank(new Bank(bankName));

        String borrowerName = params.get(1);
        paymentRequest.setBorrower(new Customer(borrowerName));

        try {
            Double lumpSumAmount = Double.valueOf(params.get(2));
            paymentRequest.setLumpSumAmount(lumpSumAmount);
        } catch (NumberFormatException ex) {
            throw new InvalidPaymentCommandException("lumpSumAmount is not valid  " + params.get(2), ex);
        }

        try {
            Integer emiNumber = Integer.valueOf(params.get(3));
            paymentRequest.setEmiNumber(emiNumber);
        } catch (NumberFormatException ex) {
            throw new InvalidPaymentCommandException("Emi number is not valid " + params.get(3), ex);
        }
        return paymentRequest;
    }
}