package com.geekbrains.task4;
import java.util.Scanner;

public class Task4 {

        public static void main(String[] args) {

            Task t = new Task();
            t.start();
        }
    }

    class Task extends Thread {
        public void run() {
            Shop a = new Shop();
            Manufacturer t1 = new Manufacturer(a);
            Consumers t2 = new Consumers(a);
            t1.start();
            t2.start();
        }
    }

    class Manufacturer extends Thread {

        private final Shop app;

        public Manufacturer(Shop app) {

            this.app = app;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                if(app.count() == 4){
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                        Thread.currentThread().interrupt();
                    }
                }
                app.add();
            }
        }
    }

    class Consumers extends Thread {

        private Shop app;

        public Consumers(Shop app) {

            this.app = app;
        }

        public void run() {
            while (!Thread.currentThread().isInterrupted()){
                try {
                    Thread.sleep(700);
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                app.take();
            }
        }
    }

    class Shop{
        name products = new name();

        public void add() {
            products.write2((int) (Math.random() * 9));
            products.print();
        }

        public int count() {

            return products.Count();
        }

        public void take() {

            products.Take();
        }
    }

    class list {
        public int data;
        public list next;
        public list prev;

        public list(int data) {
            this.data = data;
        }
    }
    class name {
        Scanner in = new Scanner(System.in);
        private list head;
        private list tail;

        public name() {
            head = null;
            tail = null;
        }

        private boolean isEmpty() {

            return head == null;
        }

        public void remove() {
            if(head != null) {
                head = head.next;
            }
        }

        public void write2(int data) {
            list t = new list(data);
            if (isEmpty())
                head = t;
            else
                tail.next = t;
            t.prev = tail;
            tail = t;
        }

        public void print() {
            list t = head;
            System.out.print("{");
            while (t != null) {
                if (t.next == null) {
                    System.out.print(t.data + ".");
                    t = t.next;
                } else {
                    System.out.print(t.data + ", ");
                    t = t.next;
                }
            }
            System.out.print("}");
            System.out.println("");
        }

        public void Take(){
            list t = head;
            if(t != null) {
                System.out.println("Покупатель взял:" + t.data);
                remove();
            }
        }

        public int Count(){
            list t = head;
            int count = 0;
            while(t != null){
                t = t.next;
                count++;
            }
            return count;
        }
}
