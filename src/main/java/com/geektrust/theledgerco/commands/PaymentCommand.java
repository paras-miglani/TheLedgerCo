package com.geektrust.theledgerco.commands;

import com.geektrust.theledgerco.domain.Bank;
import com.geektrust.theledgerco.domain.Customer;
import com.geektrust.theledgerco.model.PaymentRequest;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.BankNotFoundException;
import com.geektrust.theledgerco.exceptions.CustomerNotFoundException;
import com.geektrust.theledgerco.exceptions.InvalidPaymentCommandException;

import java.util.List;

public class PaymentCommand implements Command {
    private final LedgerService ledgerService;

    public PaymentCommand(LedgerService ledgerService) {
        this.ledgerService = ledgerService;
    }

    @Override
    public void execute(List<String> params) throws InvalidPaymentCommandException, BankNotFoundException,
            CustomerNotFoundException {
        ledgerService.addPayment(createPaymentRequest(params));
    }

    private PaymentRequest createPaymentRequest(List<String> params) throws InvalidPaymentCommandException {
        if (params.size() != 4) {
            throw new InvalidPaymentCommandException("Parameter must be of size 4");
        }
        PaymentRequest paymentRequest = new PaymentRequest();
        String bankName = params.get(0);
        paymentRequest.setBank(new Bank(bankName));

        String borrowerName = params.get(1);
        paymentRequest.setBorrower(new Customer(borrowerName));

        try {
            Double lumpSumAmount = Double.valueOf(params.get(2));
            paymentRequest.setLumpSumAmount(lumpSumAmount);
        } catch (NumberFormatException ex) {
            throw new InvalidPaymentCommandException("lumpSumAmount is not valid " + params.get(2), ex);
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
