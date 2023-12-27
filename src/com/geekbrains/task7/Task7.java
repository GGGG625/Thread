package com.geekbrains.task7;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Task7 {


    public class Main {
        static int x1 = 3;
        static int y1 = 3;
        static int x2 = 3;
        static int y2 = 3;
        static int[][] matrix1 = new int[x1][y1];
        static int[][] matrix2 = new int[x2][y2];
        static int[][] matrix2T = new int[y2][x2];
        public void main(String[] args) throws InterruptedException {
            int counter = 0;
            for(int i = 0; i < x1; i++) {
                for(int j = 0; j < y1; j++) {
                    matrix1[i][j] = counter;
                    counter++;
                }
            }
            for(int i = 0; i < x2; i++) {
                for(int j = 0; j < y2; j++) {
                    matrix2[i][j] = counter;
                    counter++;
                }
            }
            for(int i = 0; i < x2; i++) {
                for(int j = 0; j < y2; j++) {
                    matrix2T[i][j] = matrix2[j][i];
                }
            }

            int size = x1 * y2; //Это количество потоков
            CountDownLatch countDownLatch = new CountDownLatch(size);
            ExecutorService executorService = Executors.newFixedThreadPool(size);
            for (int i = 0; i < x1; i++) {
                for (int j = 0; j < y2; j++) {
                    executorService.submit(new Calculator(matrix1[i], i, matrix2T[j], j, countDownLatch));
                }
            }
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
            countDownLatch.await();
            Calculator.printResultMatrix();
        }
    }


    public class Calculator extends Thread {
        private static int[][] result = new int[3][3];
        private final int i;
        private final int j;
        private final CountDownLatch countDownLatch;
        private final int[] row;
        private final int[] column;

        public Calculator(int[] row,int i, int[] column,int j, CountDownLatch size) {
            this.row = row;
            this.i = i;
            this.column = column;
            this.j = j;
            this.countDownLatch = size;
        }

        public static void printResultMatrix() {
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    System.out.print(result[i][j] + " ");
                }
                System.out.println();
            }
        }

        @Override
        public void run() {
            int value = 0;
            for (int k = 0; k < 3; k++) {
                value += row[k] * column[k];
            }
            Calculator.result[i][j] = value;
            countDownLatch.countDown();
        }
    }

}
