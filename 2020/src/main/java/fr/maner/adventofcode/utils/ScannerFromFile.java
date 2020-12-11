package fr.maner.adventofcode.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class ScannerFromFile {

    private final Scanner scanner;

    public ScannerFromFile(URL url) throws FileNotFoundException, URISyntaxException {
        this.scanner = new Scanner(new File(url.toURI()));
    }

    public static ScannerFromFile buildScan(Class<?> c, String fileName) throws FileNotFoundException, URISyntaxException {
        return new ScannerFromFile(c.getResource("/" + fileName));
    }

    public int nextInt() {
        return this.scanner.nextInt();
    }

    public String nextLine() {
        return this.scanner.nextLine();
    }

    public boolean hasNextInt() {
        return this.scanner.hasNextInt();
    }

    public boolean hasNextLine() {
        return this.scanner.hasNextLine();
    }

    public void close() {
        this.scanner.close();
    }
}
