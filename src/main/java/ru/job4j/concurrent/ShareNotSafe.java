package ru.job4j.concurrent;

import java.util.List;

public class ShareNotSafe {
    public static void main(String[] args) throws InterruptedException {
        UserCache cache = new UserCache();
        User user = User.of("name");
        User user2 = User.of("name2");
        User user3 = User.of("name3");
        cache.add(user);
        cache.add(user2);
        cache.add(user3);
        Thread first = new Thread(
                () -> {
                    user.setName("rename");
                    user2.setName("rename2");
                    user3.setName("rename3");
                }
        );
        first.start();
        first.join();
        List<User> newList = cache.findAll();
        for (var el: newList) {
            System.out.println(el.getName());
        }
    }
}