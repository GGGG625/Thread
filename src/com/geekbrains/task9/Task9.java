package com.geekbrains.task9;
import java.util.*;
import java.util.concurrent.*;
import java.util.*;
import java.util.concurrent.*;


public class Task9 {

    class RobotTask {
        private String name;
        private Runnable task;
        private int priority;
        private long delaySeconds;
        private long timeRemaining; // Добавлено поле для хранения времени оставшегося до выполнения

        public RobotTask(String name, Runnable task, int priority, long delaySeconds) {
            this.name = name;
            this.task = task;
            this.priority = priority;
            this.delaySeconds = delaySeconds;
            this.timeRemaining = delaySeconds * 1000; // Изначально устанавливаем полное время задержки
        }

        public String getName() {
            return name;
        }

        public Runnable getTask() {
            return task;
        }

        public int getPriority() {
            return priority;
        }

        public long getDelaySeconds() {
            return delaySeconds;
        }

        public long getTimeRemaining() {
            return timeRemaining;
        }

        public void updateTimeRemaining(long timeElapsed) {
            this.timeRemaining -= timeElapsed;
        }
    }

    public class RobotTaskManager {
        private final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        private final PriorityQueue<RobotTask> taskQueue = new PriorityQueue<>(Comparator.comparingInt(RobotTask::getPriority));
        private final List<RobotTask> completedTasks = new ArrayList<>();
        private final Map<String, ScheduledFuture<?>> taskFutures = new HashMap<>();

        public void addTask(RobotTask robotTask) {
            taskQueue.add(robotTask);
            ScheduledFuture<?> future = executorService.schedule(() -> {
                robotTask.getTask().run();
                taskQueue.remove(robotTask);
                completedTasks.add(robotTask);
                taskFutures.remove(robotTask.getName());
            }, robotTask.getDelaySeconds(), TimeUnit.SECONDS);
            taskFutures.put(robotTask.getName(), future);
            scheduleTaskDisplay(robotTask);
        }

        private void scheduleTaskDisplay(RobotTask task) {
            ScheduledFuture<?> future = executorService.scheduleAtFixedRate(() -> {
                task.updateTimeRemaining(1000); // Обновляем время оставшееся до выполнения каждую секунду
            }, 0, 1, TimeUnit.SECONDS);
            taskFutures.put(task.getName() + "_display", future);
        }

        public void removeTask(String taskName) {
            taskQueue.removeIf(task -> task.getName().equals(taskName));
            ScheduledFuture<?> future = taskFutures.remove(taskName);
            ScheduledFuture<?> displayFuture = taskFutures.remove(taskName + "_display");
            if (future != null) {
                future.cancel(true);
            }
            if (displayFuture != null) {
                displayFuture.cancel(true);
            }
        }

        public void displayTasks() {
            System.out.println("Current tasks:");
            for (RobotTask task : taskQueue) {
                System.out.println("- " + task.getName() + " (Priority: " + task.getPriority() +
                        ", Delay: " + task.getDelaySeconds() + " s, Time Remaining: " + task.getTimeRemaining() + " ms)");
            }
        }

        public void displayCompletedTasks() {
            System.out.println("Completed tasks:");
            for (RobotTask completedTask : completedTasks) {
                System.out.println("- " + completedTask.getName() + " (Priority: " + completedTask.getPriority() +
                        ", Delay: " + completedTask.getDelaySeconds() + " s)");
            }
        }

        public void shutdown() {
            executorService.shutdown();
        }

        public void main(String[] args) {
            RobotTaskManager taskManager = new RobotTaskManager();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Выберите действие:");
                System.out.println("1. Добавить задачу");
                System.out.println("2. Удалить задачу");
                System.out.println("3. Отобразить текущие задачи");
                System.out.println("4. Просмотреть выполненные задачи");
                System.out.println("5. Завершить программу");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.print("Введите имя задачи: ");
                        String taskName = scanner.nextLine();
                        System.out.print("Введите приоритет задачи: ");
                        int priority = scanner.nextInt();
                        System.out.print("Введите задержку перед выполнением задачи в секундах: ");
                        long delaySeconds = scanner.nextLong();
                        scanner.nextLine();

                        RobotTask newTask = new RobotTask(taskName, () -> System.out.println("Executing " + taskName), priority, delaySeconds);
                        taskManager.addTask(newTask);
                        System.out.println("Задача добавлена!");
                        break;

                    case 2:
                        System.out.print("Введите имя задачи для удаления: ");
                        String taskToRemove = scanner.nextLine();
                        taskManager.removeTask(taskToRemove);
                        System.out.println("Задача удалена (если существовала)!");
                        break;

                    case 3:
                        taskManager.displayTasks();
                        break;

                    case 4:
                        taskManager.displayCompletedTasks();
                        break;

                    case 5:
                        taskManager.shutdown();
                        scanner.close();
                        System.exit(0);

                    default:
                        System.out.println("Неверный выбор. Пожалуйста, выберите снова.");
                        break;
                }
            }
        }
    }
}



