package ru.job4j.concurrent.synchronizer;

import java.util.concurrent.Exchanger;

public class ExchangerExample {
    public static void main(String[] args) {
        Exchanger<String> exchanger = new Exchanger<>();

        // Поток Producer
        Thread producerThread = new Thread(() -> {
            try {
                String message = "Hello from Producer";
                System.out.println("Producer sends: " + message);

                // Отправляем значение Producer'а и получаем значение от Consumer'а
                String receivedMessage = exchanger.exchange(message);
                System.out.println("Producer received: " + receivedMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Поток Consumer
        Thread consumerThread = new Thread(() -> {
            try {
                String message = "Hello from Consumer";
                System.out.println("Consumer sends: " + message);

                // Отправляем значение Consumer'а и получаем значение от Producer'а
                String receivedMessage = exchanger.exchange(message);
                System.out.println("Consumer received: " + receivedMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        // Запускаем потоки
        producerThread.start();
        consumerThread.start();
    }
}
