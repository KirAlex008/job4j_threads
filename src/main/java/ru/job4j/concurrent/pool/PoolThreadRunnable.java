package ru.job4j.concurrent.pool;

public class PoolThreadRunnable implements Runnable {

    private Thread        thread    = null;
    private SimpleBlockingQueue taskQueue = null;
    private boolean       isStopped = false;

    public PoolThreadRunnable(SimpleBlockingQueue queue) {
        taskQueue = queue;
    }

    public void run() {
        this.thread = Thread.currentThread();
        while (!isStopped()) {
            try {
                Runnable runnable = (Runnable) taskQueue.poll();
                runnable.run();
            } catch (Exception e) {
                //log or otherwise report exception,
                //but keep pool thread alive.
            }
        }
    }

    public synchronized void doStop() {
        isStopped = true;
        //break pool thread out of dequeue() call.
        this.thread.interrupt();
    }

    public synchronized boolean isStopped() {
        return isStopped;
    }
}
