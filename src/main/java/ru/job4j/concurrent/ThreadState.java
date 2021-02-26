package ru.job4j.concurrent;

public class ThreadState {
    public static void main(String[] args) {
        Thread first = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        first.start();
        Thread second = new Thread(
                () -> System.out.println(Thread.currentThread().getName())
        );
        second.start();
        while (!(second.getState() == Thread.State.TERMINATED && first.getState() == Thread.State.TERMINATED)) {
            System.out.println(first.getState());
            System.out.println(second.getState());
        }
        System.out.println("Program closed");
    }
}