package com.geekbrains.task5;

public class Task5 {
        static void PrintMat(int[][] A){
            for (int[] ints : A) {
                for (int j = 0; (ints != null && j < ints.length); j++) {
                    System.out.print(ints[j] + " ");
                }
                System.out.println();
            }
            System.out.println();
        }


        public static void main(String[] args) throws InterruptedException {
            int count = 0;
            int k11 = 3;
            int k12 = 3;
            int k21 = 3;
            int k22 = 3;
            int[][] matrix1 = new int[k11][k12];
            int[][] matrix2 = new int[k12][k22];
            int[][] matrix2T = new int[k12][k22];


            for(int i = 0; i < k11; i++) {
                for(int j = 0; j < k12; j++) {
                    matrix1[i][j] = count + ((int) (Math.random() * 9));
                    count++;
                }
            }
            PrintMat(matrix1);

            for(int i = 0; i < k21; i++) {
                for(int j = 0; j < k22; j++) {
                    matrix2[i][j] = count+ ((int) (Math.random() * 9));
                    count++;
                }
            }
            PrintMat(matrix2);

            for(int i = 0; i < k21; i++) {
                for(int j = 0; j < k22; j++) {
                    matrix2T[i][j] = matrix2[j][i];
                }
            }
            //PrintMat(matrix2T);

            multiply[][] thread = new multiply[k12][k22];
            for(int i = 0; i < k11; i++) {
                for(int j = 0; j < k22; j++) {
                    thread[i][j] = new multiply(matrix1[i],matrix2T[j]);
                    thread[i][j].start();
                }
            }
            for(int i = 0; i < k11; i++) {
                for(int j = 0; j < k22; j++) {
                    thread[i][j].join();//ожиданет конца другого потока
                }
            }
            for(int i = 0; i < k11; i++) {
                for(int j = 0; j < k22; j++) {
                    System.out.print(thread[i][j].getResult() + " ");
                }
                System.out.println("");
            }
        }
        static class multiply extends Thread {
            private int result;
            private final int[] row;
            private final int[] column;
            public multiply(int[] t, int[] m) {
                row = t;
                column = m;
            }
            public int getResult()
            {
                return result;
            }
            @Override
            public void run() {
                int temp = 0;
                for (int k = 0; k < row.length; k++) {
                    temp += row[k] * column[k];
                }
                result = temp;
            }
        }
}









