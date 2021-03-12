package ru.job4j.concurrent;

import net.jcip.annotations.NotThreadSafe;
import net.jcip.annotations.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class UserCache {
    private final ConcurrentHashMap<Integer, User> users = new ConcurrentHashMap<>();
    private final AtomicInteger id = new AtomicInteger();

    public void add(User user) {
        users.put(id.incrementAndGet(), User.of(user.getName()));
    }

    public User findById(int id) {
        return User.of(users.get(id).getName());
    }

    public List<User> findAll() {
        List<User> copyOfUsers = new ArrayList<>();
        for (var el : users.values()) {
            copyOfUsers.add(User.of(el.getName()));
        }
        return copyOfUsers;
    }
}