package com.taskengine.worker;

import com.taskengine.model.Task;
import com.taskengine.service.TaskQueueService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class Worker {

    private final TaskQueueService queueService;

    public Worker(TaskQueueService queueService){
        this.queueService = queueService;
    }

    @PostConstruct
    public void startWorker(){

        int numberOfWorkers = 3;

        for(int i = 1; i <= numberOfWorkers; i++){

            int workerId = i;

            new Thread(() -> {

                while(true) {

                    Task redisTask = queueService.getTask();

                    if(redisTask == null) continue;

                    // 🔥 Get original object from map
                    Task task = queueService.getExistingTask(redisTask.getId());

                    if(task == null) continue;

                    System.out.println("Worker-" + workerId + " Processing: " + task.getId());

                    try {
                        if(Math.random() < 0.5){
                            throw new RuntimeException("Simulated Failure");
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

                            // 🔥 Re-add for retry
                            queueService.addTask(task);

                        } else {
                            System.out.println("FAILED permanently: " + task.getId());

                            task.setStatus("FAILED");
                            queueService.updateTask(task);
                        }
                    }
                }

            }, "Worker-" + workerId).start();
        }
    }
}