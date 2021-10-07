package com.geektrust.theledgerco.factories;

import com.geektrust.theledgerco.commands.BalanceCommand;
import com.geektrust.theledgerco.commands.Command;
import com.geektrust.theledgerco.commands.LoanCommand;
import com.geektrust.theledgerco.commands.PaymentCommand;
import com.geektrust.theledgerco.domain.enums.CommandName;
import com.geektrust.theledgerco.domain.service.LedgerService;
import com.geektrust.theledgerco.exceptions.ClientException;
import com.geektrust.theledgerco.exceptions.CommandNotFoundException;
import com.geektrust.theledgerco.io.IO;
import com.geektrust.theledgerco.model.LoanRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class CommandFactoryTest {
    @Mock
    LedgerService ledgerService;
    @Mock
    IO io;
    @InjectMocks
    CommandFactory factory;

    @Test
    void shouldInitializeCommandsMap() {
        Map<CommandName, Command> commands = factory.getCommands();
        assertEquals(commands.size(), 3);
        assertTrue(commands.get(CommandName.LOAN) instanceof LoanCommand);
        assertTrue(commands.get(CommandName.BALANCE) instanceof BalanceCommand);
        assertTrue(commands.get(CommandName.PAYMENT) instanceof PaymentCommand);
    }

    @Test
    void shouldThrowExceptionWithInvalidCommandName() {
        assertThrows(CommandNotFoundException.class, () -> factory.executeCommand("invalidCommand",
                Collections.emptyList()));
    }

    @Test
    void shouldExecuteLoanCommand() throws ClientException, CommandNotFoundException {
        List<String> params = new ArrayList();
        params.add("MBI");
        params.add("Harry");
        params.add("10000");
        params.add("3");
        params.add("7");
        factory.executeCommand("LOAN", params);
        Mockito.verify(ledgerService).addLoan(any(LoanRequest.class));
    }
}