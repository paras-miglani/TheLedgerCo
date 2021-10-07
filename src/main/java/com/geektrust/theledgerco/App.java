package com.geektrust.theledgerco;

import com.geektrust.theledgerco.domain.LedgerCo;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.factories.CommandFactory;
import com.geektrust.theledgerco.handler.CommandHandler;
import com.geektrust.theledgerco.io.FileIO;
import com.geektrust.theledgerco.io.IO;

import java.io.IOException;

public class App {
    public static void main(String[] args) throws IOException {
        LedgerService ledgerService = new LedgerService(new LedgerCo());
        IO io = new FileIO(args[0], System.out);
//        IO io = new ConsoleIO(System.in, System.out);
        CommandFactory commandFactory = new CommandFactory(ledgerService, io);
        new CommandHandler(io, commandFactory).processTransactions();
    }
}
