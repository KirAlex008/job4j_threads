package ru.job4j.concurrent.parallelsearch;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSearch {

    private static <T> int linearSearch(T[] array, T key, int from, int to) {
        int index = -1;
        for (int i = from; i <= to; i++) {
            if (Objects.equals(key, array[i])) {
                index = i;
                break;
            }
        }
        return index;
    }

    private static class SearchTask<T> extends RecursiveTask<Integer> {

        private final int start;
        private final int end;
        private final T[] array;
        private final T key;

        public SearchTask(int start, int end, T[] array, T key) {
            this.start = start;
            this.end = end;
            this.array = array;
            this.key = key;
        }

        @Override
        protected Integer compute() {
            if (end - start  + 1 <= 10) {
                return linearSearch(array, key, start, end);
            }
            int mid = (start + end) / 2;
            ParallelSearch.SearchTask<T> left = new ParallelSearch.SearchTask<>(start, mid, array, key);
            ParallelSearch.SearchTask<T> right = new ParallelSearch.SearchTask<>(mid + 1, end, array, key);
            left.fork();
            right.fork();
            return Math.max(left.join(), right.join());
        }
    }

    public static void main(String[] args) {
        Integer[] numbers = new Integer[100];
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt(100);
        }
        numbers[98] = 10;

        ForkJoinPool pool = new ForkJoinPool(Runtime.getRuntime(). availableProcessors());
        pool.invoke(new SearchTask<>(0, numbers.length - 1, numbers, 10));
        System.out.println(pool.invoke(new SearchTask<>(0, numbers.length - 1, numbers, 10)));
    }
}
