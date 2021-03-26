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
        User rsl = storage.putIfAbsent(user.getId(), user);
        if (rsl == null) {
            flag = true;
        }
        return flag;
    }

    public synchronized boolean update(User user) {
        boolean flag = false;
        User rsl = storage.replace(user.getId(), user);
        if (rsl == null) {
            flag = true;
        }
        return flag;
    }

    public synchronized boolean delete(User user) {
        boolean flag = false;
        flag = storage.remove(user.getId(), user);
        return flag;
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean flag = false;
        User first = storage.get(fromId);
        User second = storage.get(toId);
        if (first != null && second != null && first.getAmount() - amount >= 0) {
            int minus = first.getAmount() - amount;
            int plus = second.getAmount() + amount;
            first.setAmount(minus);
            second.setAmount(plus);
            update(first);
            update(second);
            flag = true;
        }
        return flag;
    }
}
