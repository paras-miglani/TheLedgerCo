package com.geektrust.theledgerco.commands;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import com.geektrust.theledgerco.model.LoanRequest;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.CustomerAlreadyExistException;
import com.geektrust.theledgerco.exceptions.InvalidLoanCommandException;

import java.util.List;

public class LoanCommand implements Command {
    private LedgerService ledgerService;

    public LoanCommand(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Override
    public void execute(List<String> params) throws InvalidLoanCommandException, CustomerAlreadyExistException {
        ledgerService.addLoan(createLoanRequest(params));
    }

    private LoanRequest createLoanRequest(List<String> params) throws InvalidLoanCommandException {
        if (params.size() != 5) {
            throw new InvalidLoanCommandException("Parameter must be of size 5");
        }
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
