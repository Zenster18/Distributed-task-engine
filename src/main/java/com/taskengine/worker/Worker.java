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

    public Worker(TaskQueueService queueService){
        this.queueService = queueService;
    }

    @PostConstruct
    public void startWorkers() {

        for(int i = 1; i <= 3; i++) {

            int workerId = i;

            executor.submit(() -> {

                while(true) {

                    Task redisTask = queueService.getTask();

                    if(redisTask == null) continue;

                    Task task = queueService.getExistingTask(redisTask.getId());
                    if(task == null) continue;

                    System.out.println("Worker-" + workerId + " Processing: " + task.getId());

                    try {
                        if(Math.random() < 0.5){
                            throw new RuntimeException("Simulated failure");
                        }

                        Thread.sleep(3000);

                        task.setStatus("COMPLETED");
                        queueService.updateTask(task);

                    } catch (Exception e) {

                        task.incrementRetry();

                        if(task.getRetryCount() <= 3){

                            System.out.println("Retrying: " + task.getId() +
                                    " attempt " + task.getRetryCount());

                            task.setStatus("RETRYING");
                            queueService.updateTask(task);

                            queueService.addTask(task);

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