package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable{

    @Override
    public void run() {
        int index = 0;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                while (index < 6) {
                    marking();
                    index++;
                }
                System.out.print("\rLoaded.");
                Thread.currentThread().interrupt();

                    /*System.out.print("\r load: " + "\\");
                    //System.out.print("\rLoading : " + index  + "%");
                    Thread.sleep(500);
                    System.out.print("\r load: " + "|");
                    Thread.sleep(500);
                    System.out.print("\r load: " + "/");
                    Thread.sleep(500);*/


            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public static void main(String[] args) {
        Thread progress = new Thread(new ConsoleProgress());
        progress.run();
        try {
            Thread.sleep(10); /* симулируем выполнение параллельной задачи в течение 1 секунды. */
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        progress.interrupt();
    }

    public void marking() throws InterruptedException {
        String[] sings = new String[3];
        sings[0] = "\\";
        sings[1] = "|";
        sings[2] = "/";
        for (int i = 0; i <=2; i++) {
            System.out.println("\r load: " + sings[i]);
            Thread.sleep(500);
        }
    }
}
