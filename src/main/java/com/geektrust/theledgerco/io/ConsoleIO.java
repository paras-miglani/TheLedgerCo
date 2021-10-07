package com.geektrust.theledgerco.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class ConsoleIO implements IO {
    Scanner scanner;
    PrintStream out;

    public ConsoleIO(InputStream in, PrintStream out) {
//        this.scanner = new Scanner(in);
        this.scanner = new Scanner("LOAN IDIDI Dale 5000 1 6\n" +
                "LOAN MBI Harry 10000 3 7\n" +
                "LOAN UON Shelly 15000 2 9\n" +
                "PAYMENT IDIDI Dale 1000 5\n" +
                "PAYMENT MBI Harry 5000 10\n" +
                "PAYMENT UON Shelly 7000 12\n" +
                "BALANCE IDIDI Dale 3\n" +
                "BALANCE IDIDI Dale 6\n" +
                "BALANCE UON Shelly 12\n" +
                "BALANCE MBI Harry 12\n");
        this.out = out;
    }

    @Override
    public Stream<String> linesStream() {
        List<String> inputs = new ArrayList<>();
        while (scanner.hasNextLine()) {
            inputs.add(scanner.nextLine());
        }
        return inputs.stream();
    }

    @Override
    public void printLine(String message) {
        out.println(message);
    }
}
