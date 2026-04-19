package com.taskengine.service;

import com.taskengine.model.Task;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

@Service
public class TaskQueueService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final Map<String, Task> taskMap = new ConcurrentHashMap<>();

    private static final String QUEUE_KEY = "task_queue";

    public TaskQueueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addTask(Task task) {
        redisTemplate.opsForList().rightPush(QUEUE_KEY, task);
        taskMap.put(task.getId(), task);
    }

    public Task getTask() {
        Object task = redisTemplate.opsForList()
                .leftPop(QUEUE_KEY, 10, TimeUnit.SECONDS);

        return (Task) task;
    }

    public Task getTaskById(String id){
        return taskMap.get(id);
    }

    public Task getExistingTask(String id) {
        return taskMap.get(id);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }
}