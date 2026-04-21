package com.taskengine.worker;

import com.taskengine.model.Task;
import com.taskengine.service.TaskQueueService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class Worker {

    private final TaskQueueService queueService;
    private final ExecutorService executor = Executors.newFixedThreadPool(3);

    public Worker(TaskQueueService queueService) {
        this.queueService = queueService;
    }

    @PostConstruct
    public void startWorkers() {

        for (int i = 1; i <= 3; i++) {

            int workerId = i;

            executor.submit(() -> {

                while (true) {

                    Task task = queueService.getTask();

                    if (task == null) continue;

                    System.out.println("Worker-" + workerId + " Processing: " + task.getId());

                    try {

                        // 🔥 Simulate random failure (for testing retry)
                        if (Math.random() < 0.5) {
                            throw new RuntimeException("Simulated failure");
                        }

                        // Simulate work
                        switch (task.getType()) {

                            case "PROCESS_DATA":
                                System.out.println("Processing data: " + task.getData());
                                Thread.sleep(2000);
                                break;

                            case "GENERATE_REPORT":
                                System.out.println("Generating report: " + task.getData());
                                Thread.sleep(4000);
                                break;

                            case "LONG_TASK":
                                System.out.println("Running long task...");
                                Thread.sleep(6000);
                                break;

                            default:
                                throw new RuntimeException("Unknown task type");
                        }

                        // ✅ Success
                        task.setStatus("COMPLETED");
                        queueService.updateTask(task);

                    } catch (Exception e) {

                        task.incrementRetry();

                        if (task.getRetryCount() <= 3) {

                            System.out.println("Retrying: " + task.getId() +
                                    " attempt " + task.getRetryCount());

                            task.setStatus("RETRYING");
                            queueService.updateTask(task);

                            // 🔥 Re-add ONLY to queue (correct retry)
                            queueService.pushToQueue(task);

                        } else {

                            System.out.println("FAILED permanently: " + task.getId());

                            task.setStatus("FAILED");
                            queueService.updateTask(task);
                        }
                    }
                }
            });
        }
    }

    @PreDestroy
    public void shutdown() {
        executor.shutdown();
    }
}