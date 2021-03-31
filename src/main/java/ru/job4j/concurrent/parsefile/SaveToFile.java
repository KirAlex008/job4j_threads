package ru.job4j.concurrent.parsefile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class SaveToFile implements Output {
    private final File file;

    public SaveToFile(File file) {
        this.file = file;
    }
    @Override
    public void saveContent(String content) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
