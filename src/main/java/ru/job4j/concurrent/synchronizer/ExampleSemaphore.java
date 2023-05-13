package ru.job4j.concurrent.synchronizer;

import java.util.concurrent.Semaphore;

public class ExampleSemaphore {
    public static void main(String[] args) {
        Semaphore semaphore = new Semaphore(2); // Создаем семафор с двумя разрешениями

        // Создаем и запускаем потоки
        Thread thread1 = new Thread(new Worker(semaphore, "Поток 1"));
        Thread thread2 = new Thread(new Worker(semaphore, "Поток 2"));
        Thread thread3 = new Thread(new Worker(semaphore, "Поток 3"));
        Thread thread4 = new Thread(new Worker(semaphore, "Поток 4"));

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
    }

    static class Worker implements Runnable {
        private final Semaphore semaphore;
        private final String name;

        public Worker(Semaphore semaphore, String name) {
            this.semaphore = semaphore;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                System.out.println(name + " ждет разрешения");
                semaphore.acquire(); // Запрашиваем разрешение у семафора

                System.out.println(name + " получил разрешение");
                // Критическая секция - место, где выполняется некоторая работа
                System.out.println(name + " выполняет работу");
                Thread.sleep(2000); // Поток засыпает на 2 секунды, имитируя выполнение работы
                System.out.println(name + " завершил работу");

                System.out.println(name + " освобождает разрешение");
                semaphore.release(); // Освобождаем разрешение

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
