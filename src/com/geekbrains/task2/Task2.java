package com.geekbrains.task2;

public class Task2 {
    public Task2() {
    }

    public static void main(String[] args) {
        Thread runnable = new Thread(new MyRunnable());
        runnable.start();
        System.out.println(Thread.currentThread().getName());
    }

}
