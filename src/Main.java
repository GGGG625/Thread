public class Main {
    public static void main(String[] args) {
        Thread Thread = new MyThread();//создаем экземпляр класса
        Thread.start();
        System.out.println(java.lang.Thread.currentThread().getName());

    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName());
        for (int i = 0; i <= 100; i++) {
            if (i % 10 == 0) {
                System.out.println(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        System.out.println("Поток закончился");
    }
}