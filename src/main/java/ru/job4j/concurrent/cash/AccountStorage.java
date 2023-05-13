package ru.job4j.concurrent.cash;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;

@ThreadSafe
public class AccountStorage {
    @GuardedBy("accounts")
    private final HashMap<Integer, Account> accounts = new HashMap<>();

    public boolean add(Account account) {
        synchronized (accounts) {
            Account checkingAdd = accounts.putIfAbsent(account.id(), account);
            return Objects.equals(checkingAdd, account);
        }
    }

    public boolean update(Account account) {
        synchronized (accounts) {
            Account checkingUpdate = accounts.replace(account.id(), account);
            return Objects.equals(checkingUpdate, account);
        }
    }

    public boolean delete(int id) {
        synchronized (accounts) {
            Account checkingDelete = accounts.remove(id);
            return Objects.equals(checkingDelete.id(), id);
        }
    }

    public synchronized Optional<Account> getById(int id) {
        return Optional.ofNullable(accounts.get(id));
    }

    public boolean transfer(int fromId, int toId, int amount) {
        Optional<Account> from = this.getById(fromId);
        Optional<Account> to = this.getById(toId);
        boolean flag = false;
        synchronized (accounts) {
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