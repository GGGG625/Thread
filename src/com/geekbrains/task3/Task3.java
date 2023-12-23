package com.geekbrains.task3;


public class Task3 {
        public static void main(String[] args) {

            App t3 = new App();
            t3.start();
        }
    }

    class App extends Thread {

        public void run() {
            A a = new A();
            T1 t1 = new T1(a);
            T2 t2 = new T2(a, "message -> 5");
            T3 t3 = new T3(a, "message -> 7");
            t1.start();
            t2.start();
            t3.start();
        }
    }

    class T1 extends Thread {

        private final A app;

        public T1(A app) {
            this.app = app;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                this.app.add();
            }
        }
    }

    class T2 extends Thread {

        private final A app;
        private final String message;

        public T2(A app, String message) {
            this.app = app;
            this.message = message;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int n;
                try {
                    n = app.last();
                    System.out.println(n);
                    if (n % 5 == 0)
                        System.out.println(message);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    class T3 extends Thread {

        private final A app;
        private final String message;

        public T3(A app, String message) {
            this.app = app;
            this.message = message;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()) {
                int n;
                try {
                    n = app.last();
                    if (n % 7 == 0)
                        System.out.println(message);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    class A {

        private int d = 0;

        public synchronized void add() {
            this.d++;
            notifyAll();
        }

        public synchronized int last() throws InterruptedException {
            wait();
            return this.d;
        }
}
