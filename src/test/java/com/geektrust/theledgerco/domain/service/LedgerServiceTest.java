package com.geektrust.theledgerco.domain.service;

import com.geektrust.theledgerco.domain.Account;
import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import com.geektrust.theledgerco.domain.LedgerCo;
import com.geektrust.theledgerco.domain.Payment;
import com.geektrust.theledgerco.exceptions.BankNotFoundException;
import com.geektrust.theledgerco.exceptions.CustomerAlreadyExistException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;
import com.geektrust.theledgerco.model.BalanceRequest;
import com.geektrust.theledgerco.model.BalanceResponse;
import com.geektrust.theledgerco.model.LoanRequest;
import com.geektrust.theledgerco.model.PaymentRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LedgerServiceTest {
    @Mock
    LedgerCo ledgerCo;
    @InjectMocks
    LedgerService ledgerService;


    @Test
    void shouldAddLoan() throws CustomerAlreadyExistException, BankNotFoundException {
        Bank bank = mock(Bank.class);
        Account account = mock(Account.class);
        LoanRequest loanRequest = createLoanRequest();

        when(ledgerCo.findBank(loanRequest.getBank())).thenReturn(bank);
        when(bank.addCustomer(loanRequest.getBorrower())).thenReturn(account);

        ledgerService.addLoan(loanRequest);

        verify(ledgerCo, times(0)).addBank(loanRequest.getBank());
        verify(bank).addCustomer(loanRequest.getBorrower());
        verify(account).setLoanAmount(loanRequest.calculateAmount());
        verify(account).setNoOfEmi((loanRequest.calculateNoOfEmi()));
    }

    @Test
    void shouldAddLoanWithBankNotExistingEarlier() throws CustomerAlreadyExistException, BankNotFoundException {
        Bank bank = mock(Bank.class);
        Account account = mock(Account.class);
        LoanRequest loanRequest = createLoanRequest();

        when(ledgerCo.findBank(loanRequest.getBank())).thenThrow(new BankNotFoundException("Bank Not found " + loanRequest.getBank()));
        when(ledgerCo.addBank(loanRequest.getBank())).thenReturn(bank);
        when(bank.addCustomer(loanRequest.getBorrower())).thenReturn(account);

        ledgerService.addLoan(loanRequest);

        verify(ledgerCo).addBank(loanRequest.getBank());
        verify(bank).addCustomer(loanRequest.getBorrower());
        verify(account).setLoanAmount(loanRequest.calculateAmount());
        verify(account).setNoOfEmi((loanRequest.calculateNoOfEmi()));
    }

    @Test
    void shouldAddPayment() throws BankNotFoundException, CustomerNotFoundException {
        Bank bank = mock(Bank.class);
        Account account = mock(Account.class);
        PaymentRequest paymentRequest = createPaymentRequest();

        when(ledgerCo.findBank(paymentRequest.getBank())).thenReturn(bank);
        when(bank.findCustomerAccount(paymentRequest.getBorrower())).thenReturn(account);

        ledgerService.addPayment(paymentRequest);

        verify(bank).findCustomerAccount(paymentRequest.getBorrower());
        verify(account).addPayment(new Payment(paymentRequest.getLumpSumAmount(), paymentRequest.getEmiNumber()));
    }

    @Test
    void shouldGetBalance() throws BankNotFoundException, CustomerNotFoundException {
        Bank bank = mock(Bank.class);
        Account account = mock(Account.class);
        BalanceRequest balanceRequest = createBalanceRequest();
        Double totalAmountPaid = 1000d;
        Double noOfEmiLeft = 2d;
        BalanceResponse expectedResponse = new BalanceResponse(bank, balanceRequest.getBorrower(),
                totalAmountPaid, noOfEmiLeft);

        when(ledgerCo.findBank(balanceRequest.getBank())).thenReturn(bank);
        when(bank.findCustomerAccount(balanceRequest.getBorrower())).thenReturn(account);
        when(account.calculateTotalAmountPaid(balanceRequest.getEmiNumber())).thenReturn(totalAmountPaid);
        when(account.noOfEmisLeft(balanceRequest.getEmiNumber())).thenReturn(noOfEmiLeft);

        BalanceResponse actualResponse = ledgerService.getBalance(balanceRequest);

        verify(bank).findCustomerAccount(balanceRequest.getBorrower());
        verify(account).calculateTotalAmountPaid(balanceRequest.getEmiNumber());
        verify(account).noOfEmisLeft(balanceRequest.getEmiNumber());
        assertEquals(expectedResponse, actualResponse);
    }



    private LoanRequest createLoanRequest() {
        LoanRequest loanRequest = new LoanRequest();
        loanRequest.setBank(new Bank("bank"));
        loanRequest.setBorrower(new Customer("borrower"));
        loanRequest.setPrincipal(1000d);
        loanRequest.setNoOfYears(5);
        loanRequest.setRateOfInterest(4d);
        return loanRequest;
    }

    private PaymentRequest createPaymentRequest() {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setBank(new Bank("bank"));
        paymentRequest.setBorrower(new Customer("borrower"));
        paymentRequest.setLumpSumAmount(500d);
        paymentRequest.setEmiNumber(5);
        return paymentRequest;
    }

    private BalanceRequest createBalanceRequest() {
        BalanceRequest balanceRequest = new BalanceRequest();
        balanceRequest.setBank(new Bank("bank"));
        balanceRequest.setBorrower(new Customer("borrower"));
        balanceRequest.setEmiNumber(5);
        return balanceRequest;
    }
}