package com.geektrust.theledgerco.commands;

import com.geektrust.theledgerco.model.BalanceRequest;
import com.geektrust.theledgerco.model.BalanceResponse;
import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.BankNotFoundException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;
import com.geektrust.theledgerco.exceptions.InvalidBalanceCommandException;
import com.geektrust.theledgerco.io.IO;

import java.util.List;

public class BalanceCommand implements Command {
    private static final String SPACE = " ";
    private final LedgerService ledgerService;
    private final IO io;

    public BalanceCommand(LedgerService ledgerService, IO io) {
        this.ledgerService = ledgerService;
        this.io = io;
    }

    @Override
    public void execute(List<String> params) throws InvalidBalanceCommandException, BankNotFoundException, CustomerNotFoundException {
        BalanceResponse balanceResponse = ledgerService.getBalance(createBalanceRequest(params));
        printBalanceResponse(balanceResponse);
    }

    private void printBalanceResponse(BalanceResponse balanceResponse) {
        String message = String.join(SPACE, balanceResponse.getBankName(), balanceResponse.getBorrowerName(),
                balanceResponse.getAmountPaid(), balanceResponse.getNoOfEmisLeft());
        io.printLine(message);
    }

    private BalanceRequest createBalanceRequest(List<String> params) throws InvalidBalanceCommandException {
        if (params.size() != 3) {
            throw new InvalidBalanceCommandException("Parameter" + SPACE + "must be of size 3");
        }
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
