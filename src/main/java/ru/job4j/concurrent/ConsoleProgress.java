package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable{

    @Override
    public void run() {
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                    System.out.print("\r load: " + "\\");
                    //System.out.print("\rLoading : " + index  + "%");
                    Thread.sleep(500);
                    System.out.print("\r load: " + "|");
                    Thread.sleep(500);
                    System.out.print("\r load: " + "/");
                    Thread.sleep(500);
                index++;
                if (index == 5) {
                   Thread.currentThread().interrupt();
               }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.run();
        Thread.sleep(1); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        progress.interrupt();
    }
}
