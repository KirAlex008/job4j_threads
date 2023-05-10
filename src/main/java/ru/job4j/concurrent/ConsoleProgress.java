package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    char[] process = new char[] {'-', '\\', '|', '/'};

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            for (var sing :  process) {
                System.out.print("\r load: " + sing);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(5000);
        progress.interrupt();
    }
}
