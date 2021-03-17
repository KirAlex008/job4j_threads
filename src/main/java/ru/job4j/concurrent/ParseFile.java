package ru.job4j.concurrent;

import java.io.*;

public class ParseFile {
    private volatile File file;

    public synchronized void setFile(File f) {
        file = f;
    }

    public synchronized File getFile() {
        return file;
    }

    public synchronized String getContent() {
        String output = "";
        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String tempLine = in.readLine();
            output = output + tempLine;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }

    public synchronized String getContentWithoutUnicode() {
        String output = "";
        try (InputStream i = new FileInputStream(file)) {
        int data;
        while ((data = i.read()) > 0) {
            if (data < 0x80) {
                output += (char) data;
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return output;
    }

    public synchronized void saveContent(String content) {
        try (PrintWriter out = new PrintWriter(new BufferedOutputStream(new FileOutputStream(file)))) {
            out.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
