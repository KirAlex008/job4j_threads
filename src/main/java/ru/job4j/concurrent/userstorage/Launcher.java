package ru.job4j.concurrent.userstorage;

public class Launcher {
    public static void main(String[] args) {
        User one = new User(1, 100);
        User two = new User(2, 200);
        UserStorage storage = new UserStorage();
        storage.add(one);
        storage.add(two);
        storage.transfer(1, 2, 50);
        System.out.println(one.getAmount());
    }
}
