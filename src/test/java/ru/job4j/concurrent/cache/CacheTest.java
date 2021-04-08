package ru.job4j.concurrent.cache;

import org.junit.Assert;
import org.junit.Test;
import java.util.concurrent.atomic.AtomicReference;
import static org.hamcrest.Matchers.is;

public class CacheTest {

    @Test
    public void whenExceptionWorks() throws InterruptedException {
        AtomicReference<RuntimeException> ex = new AtomicReference<>();
        Cache cache = new Cache();
        Base first = new Base(1, "zero");
        cache.add(first);
        Thread thread1 = new Thread(
                () -> {
                    try {
                        cache.update(new Base(1, "first"));
                    } catch (OptimisticException e) {
                        ex.set(e);
                    }
                });
        Thread thread2 = new Thread(
                () -> {
                    try {
                        cache.update(new Base(1, "second"));
                    } catch (OptimisticException e) {
                        ex.set(e);
                    }
                });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        Assert.assertThat(ex.get().getMessage(), is("Versions are not equal"));

    }
}