package ru.job4j.concurrent;

public class JoinExample {
    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("Нить 1 начала работу");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Нить 1 завершила работу");
        });

        Thread thread2 = new Thread(() -> {
            System.out.println("Нить 2 начала работу");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Нить 2 завершила работу");
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        System.out.println("Все нити завершили работу");
    }
}

