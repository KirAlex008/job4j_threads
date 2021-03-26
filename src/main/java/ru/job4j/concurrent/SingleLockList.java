package ru.job4j.concurrent;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@ThreadSafe
public class SingleLockList<T> implements Iterable<T> {
    @GuardedBy("this")
    private final SimpleArray<T> list = new SimpleArray();

    public synchronized void add(T value) {
        list.add(value);
    }

    public synchronized T get(int index) {
        return list.get(index);
    }

    public synchronized SimpleArray copy(SimpleArray array) {
        SimpleArray copyOfList = new SimpleArray();
        Iterator iterator = array.iterator();
        while (iterator.hasNext()) {
            copyOfList.add(iterator.next());
        }
        /*for (var el : array) {
            copyOfList.add(el);
        }*/
        return copyOfList;
    }

    @Override
    public synchronized Iterator<T> iterator() {
        return copy(this.list).iterator();
    }


}