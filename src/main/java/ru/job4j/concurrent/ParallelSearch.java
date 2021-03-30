package ru.job4j.concurrent;

public class ParallelSearch {

    public static void main(String[] args) throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(3);

        final Thread consumer = new Thread(
                () -> {
                    while (!queue.isEmpty() || !Thread.currentThread().isInterrupted()) {
                        System.out.println(queue.poll());
                    }
                }
        );
        consumer.start();
        new Thread(
                () -> {

                        for (int index = 0; index != 3; index++) {
                            queue.offer(index);
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                                Thread.currentThread().interrupt();
                            }
                        }
                        consumer.interrupt();
                }

        ).start();
    }
}
