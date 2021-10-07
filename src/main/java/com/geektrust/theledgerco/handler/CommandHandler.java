package com.geektrust.theledgerco.handler;

import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.CommandNotFoundException;
import com.geektrust.theledgerco.exceptions.ClientException;
import com.geektrust.theledgerco.factories.CommandFactory;
import com.geektrust.theledgerco.io.IO;

import java.util.Arrays;

public class CommandHandler {
    private static final String SPACE = " ";
    private final IO io;
    private final CommandFactory commandFactory;

    public CommandHandler(IO io, CommandFactory commandFactory) {
        this.io = io;
        this.commandFactory = commandFactory;
    }

    public void processTransactions() {
        io.linesStream().forEach(this::executeTransaction);
    }

    private void executeTransaction(String line) {
        String[] tokens = line.split(SPACE);
        String commandName = tokens[0];
        try {
            commandFactory.executeCommand(commandName,
                    Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length).clone()));
        } catch (CommandNotFoundException e) {
            io.printLine("Command Not Found: " + commandName);
        } catch (ClientException clientException) {
            io.printLine(clientException.getMessage());
        }
    }
}
