package com.geektrust.theledgerco.commands;

import com.geektrust.theledgerco.exceptions.ClientException;

import java.util.List;

public interface Command {
    void execute(List<String> params) throws ClientException;
}
