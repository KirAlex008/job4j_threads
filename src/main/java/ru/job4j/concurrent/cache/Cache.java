package ru.job4j.concurrent.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class Cache {
    private Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return (memory.computeIfPresent(model.getId(), (key, value) -> {
            if (model.getVersion() != value.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            value.setVersion(value.getVersion() + 1);
            value.setName(model.getName());
            return value;
        } ) != null);
    }



    public synchronized boolean delete(Base model) {
        return memory.remove(model.getId(), model);
    }
}
