package ru.job4j.concurrent.pool;

public class ThreadPoolMain {

    public static void main(String[] args) throws Exception {

        ThreadPool threadPool = new ThreadPool(10);

        for (int i = 0; i < 10; i++) {

            int taskNo = i;
            threadPool.work(() -> {
                String message =
                        Thread.currentThread().getName()
                                + ": Task " + taskNo;
                System.out.println(message);
            });
        }

        threadPool.waitUntilAllTasksFinished();
        threadPool.shutdown();

    }
}
