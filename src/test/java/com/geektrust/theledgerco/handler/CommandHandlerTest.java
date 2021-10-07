package com.geektrust.theledgerco.handler;

import com.geektrust.theledgerco.exceptions.ClientException;
import com.geektrust.theledgerco.exceptions.CommandNotFoundException;
import com.geektrust.theledgerco.factories.CommandFactory;
import com.geektrust.theledgerco.io.IO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandHandlerTest {
    @Mock
    CommandFactory commandFactory;
    @Mock
    IO io;
    @InjectMocks
    CommandHandler commandHandler;

    @Test
    void shouldNotInteractWithFactoryWithoutParams() {
        Stream<String> stream = Stream.empty();
        when(io.linesStream()).thenReturn(stream);

        commandHandler.processTransactions();

        verifyNoInteractions(commandFactory);
    }

    @Test
    void shouldPrintNoCommandFoundWithoutWithoutValidCommand() throws ClientException, CommandNotFoundException {
        List<String> commandList = Collections.EMPTY_LIST;
        String command = "command";
        Stream<String> stream = Stream.of(command);
        when(io.linesStream()).thenReturn(stream);
        doThrow(new CommandNotFoundException()).when(commandFactory).executeCommand(command, commandList);

        commandHandler.processTransactions();
        verify(io).printLine("Command Not Found: " + command);
    }

    @Test
    void shouldThrowClientExceptionWithoutValidCommand() throws ClientException, CommandNotFoundException {
        List<String> commandList = Collections.EMPTY_LIST;
        String command = "command";
        Stream<String> stream = Stream.of(command);
        when(io.linesStream()).thenReturn(stream);
        doThrow(new ClientException("Valid Exception Message"))
                .when(commandFactory).executeCommand(command, commandList);

        commandHandler.processTransactions();
        verify(io).printLine("Valid Exception Message");
    }

    @Test
    void shouldExecuteCommands() throws ClientException, CommandNotFoundException {
        List<String> commandList = Collections.EMPTY_LIST;
        String command = "command";
        Stream<String> stream = Stream.of(command);
        when(io.linesStream()).thenReturn(stream);

        commandHandler.processTransactions();
        verify(commandFactory).executeCommand(command, commandList);
    }
}