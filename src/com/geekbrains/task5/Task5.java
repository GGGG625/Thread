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
            int k = 3;
            int[][] matrix1 = new int[k][k];
            int[][] matrix2 = new int[k][k];
            int[][] matrix2T = new int[k][k];

            for(int i = 0; i < matrix1.length; i++) {
                for(int j = 0; j < matrix1.length; j++) {
                    matrix1[i][j] = count;
                    count++;
                }
            }
            PrintMat(matrix1);

            for(int i = 0; i < matrix2.length; i++) {
                for(int j = 0; j < matrix2.length; j++) {
                    matrix2[i][j] = count;
                    count++;
                }
            }
            PrintMat(matrix2);

            for(int i = 0; i < matrix2T.length; i++) {
                for(int j = 0; j < matrix2T.length; j++) {
                    matrix2T[i][j] = matrix2[j][i];
                }
            }
            //PrintMat(matrix2T);

            multiply[][] thread = new multiply[3][3];
            for(int i = 0; i < matrix1.length; i++) {
                for(int j = 0; j < matrix1.length; j++) {
                    thread[i][j] = new multiply(matrix1[i],matrix2T[j]);
                    thread[i][j].start();
                }
            }
            for(int i = 0; i < matrix1.length; i++) {
                for(int j = 0; j < matrix2.length; j++) {
                    thread[i][j].join();//ожиданет конца другого потока
                }
            }
            for(int i = 0; i < matrix1.length; i++) {
                for(int j = 0; j < matrix1.length; j++) {
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
                for (int k = 0; k < 3; k++) {
                    temp += row[k] * column[k];
                }
                result = temp;
            }
        }
}




