package ru.job4j.concurrent.userstorage;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;

@ThreadSafe
public class UserStorage {
    @GuardedBy("this")
    private final Map<Integer, User> storage = new HashMap<>();

    public synchronized boolean add(User user) {
        boolean flag = false;
        Integer key = user.getId();
        if (!storage.containsKey(key)) {
            flag = true;
            storage.put(key, user);
        }
        return flag;
    }

    public synchronized boolean update(User user) {
        boolean flag = false;
        Integer key = user.getId();
        if (storage.containsKey(key)) {
            flag = true;
            storage.put(key, user);
        }
        return flag;
    }

    public synchronized boolean delete(User user) {
        boolean flag = false;
        Integer key = user.getId();
        if (storage.containsKey(key)) {
            flag = true;
            storage.remove(key);
        }
        return flag;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        User first = storage.get(fromId);
        User second =storage.get(toId);
        boolean flag = false;
        if (first.getAmount() - amount >= 0) {
            int minus = first.getAmount() - amount;
            int plus = second.getAmount() + amount;
            first.setAmount(minus);
            second.setAmount(plus);
            this.update(first);
            this.update(second);
            flag = true;
        }
        return flag;
    }
}
