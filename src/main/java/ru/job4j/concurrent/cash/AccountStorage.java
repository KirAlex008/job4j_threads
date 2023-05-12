package ru.job4j.concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("this")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public synchronized boolean add(Account account) {
        Account checkingAdd = accounts.putIfAbsent(account.id(), account);
        return Objects.equals(checkingAdd, account);
    }

    public boolean update(Account account) {
        synchronized (this) {
            Account checkingUpdate = accounts.replace(account.id(), account);
            return Objects.equals(checkingUpdate, account);
        }
    }

    public synchronized boolean delete(int id) {
        Account checkingDelete = accounts.remove(id);
        return Objects.equals(checkingDelete.id(), id);
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> from = this.getById(fromId);
        Optional<Account> to = this.getById(toId);
        boolean flag = false;
        synchronized (this) {
            if (from.isPresent() && to.isPresent() && from.get().amount() >= amount) {
                int balanceFrom = from.get().amount() - amount;
                int balanceTo = to.get().amount() + amount;
                this.update(new Account(fromId, balanceFrom));
                this.update(new Account(toId, balanceTo));
                flag = true;
                }
            }
        return flag;
    }
}