package ru.job4j.concurrent.completablefuture;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class RolColSum {
    public static class Sums {
        private int colSum;
        private int rowSum;

        public Sums(int colSum, int rowSum) {
            this.rowSum = rowSum;
            this.colSum = colSum;
        }

        public Sums() {
            this.rowSum = 0;
            this.colSum = 0;
        }

        public int getRowSum() {
            return rowSum;
        }

        public void setRowSum(int rowSum) {
            this.rowSum = rowSum;
        }

        public int getColSum() {
            return colSum;
        }

        public void setColSum(int colSum) {
            this.colSum = colSum;
        }

        @Override
        public String toString() {
            return "Sums{" + "rowSum=" + rowSum + ", colSum=" + colSum + '}';
        }
    }

    public static Sums[] sum(int[][] matrix) {
        Sums[] sums = new Sums[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            sums[i] = new Sums();
        }
        int limit = matrix.length;
        int tempI = 0;
        int tempJ = 0;
        for (int j = 0; j < limit; j++) {
            for (int i = 0; i < limit; i++) {
                tempI = tempI + matrix[j][i]; // sum of string
                tempJ = tempJ + matrix[i][j]; // sum of column
                sums[j].setRowSum(tempI);
                sums[j].setColSum(tempJ);
            }
            tempI = 0;
            tempJ = 0;
        }
        return sums;
    }



    public static Sums[] asyncSum(int[][] matrix) throws ExecutionException, InterruptedException {
        Map<Integer, CompletableFuture<Sums>> map = new HashMap<>();
        int len = matrix.length;
        Sums[] sums = new Sums[len];
        for (int j = 0; j <= len / 2; j++) {
            map.put(j, getTask(matrix, j));
            if (j < len - 1 - j) {
                map.put(len - 1 - j, getTask(matrix, len - 1 - j));
            }
        }
        for (Integer key : map.keySet()) {
            sums[key] = map.get(key).get();
        }
        return sums;
    }

    private static CompletableFuture<Sums> getTask(int[][] matrix, int j) {
        return CompletableFuture.supplyAsync(() -> {
            int limit = matrix.length;
            int tempI = 0;
            int tempJ = 0;
            Sums sums = new Sums();
            for (int i = 0; i < limit; i++) {
                    tempI = tempI + matrix[j][i]; // sum of string
                    tempJ = tempJ + matrix[i][j]; // sum of column
                    sums.setRowSum(tempI);
                    sums.setColSum(tempJ);
                }

            return sums;
        });
    }

}
