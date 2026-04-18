package com.taskengine.worker;

import com.taskengine.model.Task;
import com.taskengine.service.TaskQueueService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;


@Component
public class Worker {
    private final TaskQueueService queueService;
    public Worker(TaskQueueService queueService){
        this.queueService=queueService;
    }

    @PostConstruct
    public void startWorker(){
        int numberOfWorkers=3;
        for(int i=1;i<=numberOfWorkers;i++){
            int workerId=i;
            new Thread(()-> {
                while(true) {
                    Task task = queueService.getTask();

                        System.out.println("Worker-" + workerId + "Processing task: " + task.getId());
                        try {
                            if(Math.random()<0.5){
                                throw new RuntimeException("Simulated Failure");
                            }
                            Thread.sleep(3000);
                            task.setStatus("COMPLETED");
                        } catch (Exception e) {
                            task.incrementRetry();
                            if(task.getRetryCount()<=3){
                                System.out.println("Retrying task: "+task.getId()+"attempt "+task.getRetryCount());
                                task.setStatus("RETRYING");
                            }
                            else{
                                System.out.println("Task FAILED permanently "+task.getId());
                                task.setStatus("FAILED");
                            }

                        }
                }
            }, "Worker-" + workerId).start();
        }

    }

}
