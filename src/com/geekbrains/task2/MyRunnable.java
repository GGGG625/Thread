package com.geekbrains.task2;

class MyRunnable implements Runnable {
    MyRunnable() {
    }

    public void run() {
        System.out.println(Thread.currentThread().getName());

        for(int i = 0; i <= 100; ++i) {
            if (i % 10 == 0) {
                System.out.println(i);

                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException var3) {
                    throw new RuntimeException(var3);
                }
            }
        }

        System.out.println("Поток закончился");
    }
}

