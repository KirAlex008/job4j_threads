package ru.job4j.concurrent;

public class ThreadStop {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(
                () -> {
                    int count = 0;
                    while (!Thread.currentThread().isInterrupted()) {
                        System.out.println(count++);
                        System.out.println(Thread.currentThread().getName());
                        System.out.println(Thread.currentThread().getState());
                    }
                }
        );
        thread.start();
        Thread.sleep(1);
        thread.interrupt();
    }
}