package com.geekbrains.task8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Task8 {

    public class Main {
        public static List<Task> taskList = new ArrayList<>();
        public void main(String[] args) {
            ExecutorService executorService = Executors.newFixedThreadPool(2);
            Scanner scanner = new Scanner(System.in);
            int counter;
            boolean running = true;
            while(running) {
                System.out.println("Выберите пункт меню:\n1. Добавить задачу\n2. Удалить задачу\n3. Вывести задачи и их статус\n4. Завершение работы");
                counter = scanner.nextInt();
                switch (counter) {
                    case (1): {
                        System.out.print("Введите название задачи: ");
                        String scan = scanner.next();
                        Task task = new Task(scan);
                        taskList.add(task);
                        executorService.submit(task :: executeTask);
                        break;
                    }
                    case(2): {
                        System.out.print("Введите название задачи: ");
                        String scan = scanner.next();
                        String taskName = new String(scan);
                        Task newTask = new Task(taskName);
                        if(Arrays.asList(taskList).contains(newTask)) {
                            taskList.remove(newTask);
                        }

                        break;
                    }
                    case (3): {
                        for(int i = 0; i < taskList.size(); i++)
                        {
                            taskList.get(i).viewTask();
                        }
                        break;
                    }
                    case (4): {
                        running = false;
                    }
                }
            }
        }
    }

    class ExecutorSchedulerService {
        private ExecutorService executorService;
        private List<Task> taskList = new ArrayList<>();
        private AtomicBoolean isRunning = new AtomicBoolean(false);

        public ExecutorSchedulerService(int threadPoolSize) {
            this.executorService = Executors.newFixedThreadPool(threadPoolSize);
        }

        public void addTask(Task task) {
            taskList.add(task);
        }

        public void start() {
            if (isRunning.compareAndSet(false, true)) {
                for (Task task : taskList) {
                    executorService.submit(task::executeTask);
                }
            }
        }

        public void stop() {
            if (isRunning.compareAndSet(true, false)) {
                executorService.shutdown();
            }
        }
    }

    class Task {
        private String name;
        private String status;
        //
        public Task(String name) {
            this.name = name;
            this.status = "Не выполняется";
        }

        public String getName() {
            return name;
        }

        public void executeTask() {
            System.out.println("Выполнение задачи: " + name);
            this.status = "Выполняется";
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Task newTask = new Task(name);
            System.out.println(Main.taskList);
            System.out.println("Задача выполнена: " + name);
            this.status = "Выполнен";
        }

        public void viewTask()
        {
            System.out.println("Статус задачи " + name + ": " + status);
        }
    }

}
