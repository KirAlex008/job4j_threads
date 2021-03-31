package ru.job4j.concurrent.parsefile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.function.Predicate;

public class ContentFromFile implements Input {
    private final Predicate<Character> filter;
    private final File file;

    public ContentFromFile(File file, Predicate<Character> filter) {
        this.file = file;
        this.filter = filter;
    }

    @Override
    public String content() {
        StringBuilder output = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            int data;
            while ((data = in.read()) > 0) {
                Character ch = (char) data;
                if (filter.test(ch)) {
                    output.append(output.toString()).append(ch);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output.toString();
    }
}
