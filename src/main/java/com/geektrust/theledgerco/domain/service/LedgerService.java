package com.geektrust.theledgerco.domain.service;

import com.geektrust.theledgerco.domain.Account;
import com.geektrust.theledgerco.model.BalanceRequest;
import com.geektrust.theledgerco.model.BalanceResponse;
import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.LedgerCo;
import com.geektrust.theledgerco.model.LoanRequest;
import com.geektrust.theledgerco.domain.Payment;
import com.geektrust.theledgerco.model.PaymentRequest;
import com.geektrust.theledgerco.exceptions.BankNotFoundException;
import com.geektrust.theledgerco.exceptions.CustomerAlreadyExistException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;

public class LedgerService {
    private final LedgerCo ledgerCo;

    public LedgerService(LedgerCo ledgerCo) {
        this.ledgerCo = ledgerCo;
    }

    public void addLoan(LoanRequest loanRequest) throws CustomerAlreadyExistException {
        Account borrowerAccount = createBorrowerAccount(loanRequest);
        borrowerAccount.setLoanAmount(loanRequest.calculateAmount());
        borrowerAccount.setNoOfEmi(loanRequest.calculateNoOfEmi());
    }

    public void addPayment(PaymentRequest paymentRequest) throws BankNotFoundException, CustomerNotFoundException {
        Bank bank = ledgerCo.findBank(paymentRequest.getBank());
        Account customerAccount = bank.findCustomerAccount(paymentRequest.getBorrower());
        customerAccount.addPayment(new Payment(paymentRequest.getLumpSumAmount(), paymentRequest.getEmiNumber()));
    }

    private Account createBorrowerAccount(LoanRequest loanRequest) throws CustomerAlreadyExistException {
        Bank bank;
        try {
            bank = ledgerCo.findBank(loanRequest.getBank());
        } catch (BankNotFoundException e1) {
            bank = ledgerCo.addBank(loanRequest.getBank());
        }
        return bank.addCustomer(loanRequest.getBorrower());
    }

    public BalanceResponse getBalance(BalanceRequest balanceRequest) throws BankNotFoundException,
            CustomerNotFoundException {
        Bank bank = ledgerCo.findBank(balanceRequest.getBank());
        Account customerAccount = bank.findCustomerAccount(balanceRequest.getBorrower());
        Double totalAmountPaid = customerAccount.calculateTotalAmountPaid(balanceRequest.getEmiNumber());
        Double noOfEmisLeft = customerAccount.noOfEmisLeft(balanceRequest.getEmiNumber());
        return new BalanceResponse(bank, balanceRequest.getBorrower(), totalAmountPaid, noOfEmisLeft);
    }
}
