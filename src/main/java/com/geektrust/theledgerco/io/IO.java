package com.geektrust.theledgerco.io;

import java.util.stream.Stream;

public interface IO {
    Stream<String> linesStream();

    void printLine(String message);
}
