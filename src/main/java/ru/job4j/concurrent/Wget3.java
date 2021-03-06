package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget3 implements Runnable {
    private final String url;
    private final int speed;

    public Wget3(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        String file = url;
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long firstPoint = System.currentTimeMillis();
            long secondPoint = 0;
            int counter = 0;
            int speedRatio = speed / 1024;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                counter++;
                if (bytesRead == 1024 && counter == 1) {
                    secondPoint = System.currentTimeMillis();
                }
                long timeFor1024 = secondPoint - firstPoint;
                Thread.sleep(1000 - speedRatio * timeFor1024);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget3(url, speed));
        wget.start();
        wget.join();
    }
}
