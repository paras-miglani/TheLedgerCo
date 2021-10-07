package com.geektrust.theledgerco.factories;

import com.geektrust.theledgerco.commands.BalanceCommand;
import com.geektrust.theledgerco.commands.Command;
import com.geektrust.theledgerco.commands.LoanCommand;
import com.geektrust.theledgerco.commands.PaymentCommand;
import com.geektrust.theledgerco.domain.enums.CommandName;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.CommandNotFoundException;
import com.geektrust.theledgerco.exceptions.ClientException;
import com.geektrust.theledgerco.io.IO;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class CommandFactory {
    Map<CommandName, Command> commands;

    public CommandFactory(LedgerService ledgerService, IO io) {
        commands = new EnumMap<>(CommandName.class);
        this.addCommand(CommandName.LOAN, new LoanCommand(ledgerService));
        this.addCommand(CommandName.PAYMENT, new PaymentCommand(ledgerService));
        this.addCommand(CommandName.BALANCE, new BalanceCommand(ledgerService, io));
    }

    private void addCommand(CommandName commandName, Command command) {
        commands.put(commandName, command);
    }

    public void executeCommand(String commandName, List<String> params)
            throws CommandNotFoundException, ClientException {
        CommandName command;
        try {
            command = CommandName.valueOf(commandName);
        } catch (IllegalArgumentException ex) {
            throw new CommandNotFoundException();
        }
        commands.get(command).execute(params);
    }

    public Map<CommandName, Command> getCommands() {
        return commands;
    }
}
