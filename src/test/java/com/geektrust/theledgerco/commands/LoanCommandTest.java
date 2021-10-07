package com.geektrust.theledgerco.commands;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.ClientException;
import com.geektrust.theledgerco.exceptions.InvalidLoanCommandException;
import com.geektrust.theledgerco.exceptions.InvalidPaymentCommandException;
import com.geektrust.theledgerco.model.LoanRequest;
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
class LoanCommandTest {
    @Mock
    LedgerService ledgerService;
    @InjectMocks
    LoanCommand command;

    @Test
    void shouldThrowExceptionIfParametersLengthIsNotValid() {
        InvalidLoanCommandException loanCommandException = assertThrows(InvalidLoanCommandException.class,
                () -> command.execute(Collections.emptyList()));
        assertEquals("Parameter must be of size 5", loanCommandException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfPrincipalValueIsNotValid() {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        String principal = "principal";
        params.add(principal);
        params.add("2");
        params.add("4");
        InvalidLoanCommandException loanCommandException = assertThrows(InvalidLoanCommandException.class,
                () -> command.execute(params));
        assertEquals("Principal is not valid " + principal, loanCommandException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfTenureValueIsNotValid() {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        params.add("1000");
        String tenure = "tenure";
        params.add(tenure);
        params.add("4");
        InvalidLoanCommandException loanCommandException = assertThrows(InvalidLoanCommandException.class,
                () -> command.execute(params));
        assertEquals("Number of years is not valid " + tenure, loanCommandException.getMessage());
    }

    @Test
    void shouldThrowExceptionIfRateOfInterestValueIsNotValid() {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        params.add("200000");
        params.add("2");
        String rateOfInterest = "rateOfInterest";
        params.add(rateOfInterest);
        InvalidLoanCommandException loanCommandException = assertThrows(InvalidLoanCommandException.class,
                () -> command.execute(params));
        assertEquals("Rate Of interest is not valid " + rateOfInterest, loanCommandException.getMessage());
    }

    @Test
    void shouldAddLoanOnnValidRequest() throws ClientException {
        List<String> params = new ArrayList<>();
        params.add("bankName");
        params.add("customerName");
        params.add("200000");
        params.add("2");
        params.add("2");
        LoanRequest loanRequest = createLoanRequest(params);

        command.execute(params);
        verify(ledgerService).addLoan(loanRequest);
    }

    private LoanRequest createLoanRequest(List<String> params) throws InvalidLoanCommandException {
        LoanRequest loanRequest = new LoanRequest();
        String bankName = params.get(0);
        loanRequest.setBank(new Bank(bankName));

        String borrowerName = params.get(1);
        loanRequest.setBorrower(new Customer(borrowerName));

        try {
            Double principal = Double.valueOf(params.get(2));
            loanRequest.setPrincipal(principal);
        } catch (NumberFormatException ex) {
            throw new InvalidLoanCommandException("Principal is not valid " + params.get(2), ex);
        }
        try {
            Integer noOfYears = Integer.valueOf(params.get(3));
            loanRequest.setNoOfYears(noOfYears);
        } catch (NumberFormatException ex) {
            throw new InvalidLoanCommandException("Number of years is not valid " + params.get(3), ex);
        }
        try {
            Double rateOfInterest = Double.valueOf(params.get(4));
            loanRequest.setRateOfInterest(rateOfInterest);
        } catch (NumberFormatException ex) {
            throw new InvalidLoanCommandException("Rate Of interest is not valid " + params.get(4), ex);
        }
        return loanRequest;

    }
}