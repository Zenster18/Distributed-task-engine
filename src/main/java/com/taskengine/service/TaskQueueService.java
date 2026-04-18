package com.taskengine.service;

import com.taskengine.model.Task;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class TaskQueueService {
    private final BlockingQueue<Task> queue= new LinkedBlockingQueue<>();
    private final Map<String,Task> taskMap=new ConcurrentHashMap<>();
    public void addTask(Task task){
        queue.offer(task);
        taskMap.put(task.getId(),task);
    }

    public Task getTask(){
        try{
            return queue.take();
        }
        catch(InterruptedException e){
            return null;
        }
    }

    public Task getTaskById(String id){
        return taskMap.get(id);
    }
}
