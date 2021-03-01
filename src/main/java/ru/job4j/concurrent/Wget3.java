package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class Wget3 implements Runnable {
    private final String url;
    private final int speed;

    public Wget3(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    @Override
    public void run() {
        /* Скачать файл */
        /* Скорость 1 КБайт в 1 секунду */
        String file = "https://raw.githubusercontent.com/peterarsentev/course_test/master/pom.xml";
        try (BufferedInputStream in = new BufferedInputStream(new URL(file).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream("pom_tmp.xml")) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long firstPoint = System.currentTimeMillis();
            long secondPoint = 0;
            int counter = 0;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                counter++;
                if (bytesRead == 1024 && counter == 1) {
                    secondPoint = System.currentTimeMillis();
                }
                long timeFor1024 = secondPoint - firstPoint;
                System.out.println(timeFor1024 + " timeFor1024");
                Thread.sleep(1000 - timeFor1024);
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        //String url = args[0];
        String url = "pom_tmp.xml";
        File file = new File(url);
        long size = file.length();
        System.out.println("length = " + size);
        //int speed = Integer.parseInt(args[1]);
        int speed = 1024;
        Thread wget = new Thread(new Wget3(url, speed));
        wget.start();
        wget.join();
        long stop = System.currentTimeMillis();
        System.out.println(stop - start + " Working time");
    }
}
