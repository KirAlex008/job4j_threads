package ru.job4j.concurrent;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount {
    private final AtomicReference<Integer> count = new AtomicReference<>();

    public void increment() {
        boolean success = false;
        do {
            Integer old = get();
            success = count.compareAndSet(old, old + 1);
            if (success == false) {
                throw new UnsupportedOperationException("Count is not impl.");
            }
        } while (success == true);
    }

    public int get() {
        return count.get();
        //throw new UnsupportedOperationException("Count is not impl.");
    }
}
