package ru.job4j.concurrent;

import org.junit.Test;
import org.junit.Assert;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {
    @Test
    public void test() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(2);
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
        });
        Thread consumer = new Thread(() -> {
            queue.poll();
            queue.poll();
            queue.poll();
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        Assert.assertEquals(0, queue.size());
    }
}