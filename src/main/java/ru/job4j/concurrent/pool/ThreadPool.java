package ru.job4j.concurrent.pool;

import java.util.LinkedList;
import java.util.List;

public class ThreadPool {
    private final int size = Runtime.getRuntime().availableProcessors();
    private final List<PoolThreadRunnable> threads = new LinkedList<>();
    private final SimpleBlockingQueue<Runnable> tasks = new SimpleBlockingQueue<>(size);
    private boolean isStopped = false;

    public ThreadPool(int maxNoOfTasks){
        for(int i=0; i<size; i++){
            PoolThreadRunnable poolThreadRunnable =
                    new PoolThreadRunnable(tasks);
            threads.add(new PoolThreadRunnable(tasks));
        }
        for(PoolThreadRunnable thread : threads){
            new Thread(thread).start();
        }
    }

    public synchronized void waitUntilAllTasksFinished() {
        while(this.tasks.size() > 0) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void work(Runnable job) throws Exception {
        if(this.isStopped) throw
                new IllegalStateException("ThreadPool is stopped");
        this.tasks.offer(job);
    }


    public synchronized void shutdown() {
        this.isStopped = true;
        for(PoolThreadRunnable thread : threads){
            thread.doStop();
        }
    }

}