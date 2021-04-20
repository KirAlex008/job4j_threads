package ru.job4j.concurrent.pool;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.LinkedList;
import java.util.Queue;

@ThreadSafe
public class SimpleBlockingQueue<T> {

    @GuardedBy("this")
    private  final Queue<T> queue = new LinkedList<>();
    @GuardedBy("this")
    private final int maxSize;

    public SimpleBlockingQueue(int maxSize) {
        this.maxSize = maxSize;
    }

    public synchronized void offer(T value) {
        try {
            while (queue.size() == maxSize) {
                wait();
                System.out.println("The queue is full. Waiting.");
            }
            queue.add(value);
            System.out.println("Element added");
            notify();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("The thread of producer is interrupted");
        }
    }

        public synchronized T poll() throws InterruptedException {
            T result = null;
            try {
                while (queue.size() == 0) {
                    System.out.println("The queue is empty. Waiting.");
                    wait();
                }
                result = queue.poll();
                System.out.println("Element received");
                notify();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("The thread of consumer is interrupted");
            }
            return result;
        }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized boolean isEmpty() {
        return queue.size() == 0;
    }
}
