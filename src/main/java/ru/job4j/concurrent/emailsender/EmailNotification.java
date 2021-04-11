package ru.job4j.concurrent.emailsender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors()
    );

    public void emailTo(User user) {
        StringBuilder subject = new StringBuilder();
        StringBuilder body = new StringBuilder();
        StringBuilder email = new StringBuilder();

        subject.append("Notification ").append(user.getUsername()).append("to email ").append(user.getEmail());
        body.append("Add a new event to ").append(user.getUsername());
        email.append(user.getEmail());

        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(subject.toString(), body.toString(), email.toString());
            }
        });

    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void send(String subject, String body, String email) {
        }
}
