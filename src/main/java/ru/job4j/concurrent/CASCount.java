package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        int old;
        do {
            old = get();
        } while (!count.compareAndSet(old, old + 1));
    }

    public int get() {
        return count.get();
    }
}
