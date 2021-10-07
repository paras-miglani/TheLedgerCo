package com.geektrust.theledgerco.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class FileIO implements IO {

    private Stream<String> linesStream;
    private PrintStream out;


    public Stream<String> linesStream() {
        return linesStream;
    }

    public FileIO(String fileName, PrintStream out) throws IOException {
        this.out = out;
        this.linesStream = Files.lines(Paths.get(fileName));
    }

    @Override
    public void printLine(String message) {
        out.println(message);
    }
}
