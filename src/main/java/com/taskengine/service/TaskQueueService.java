package com.taskengine.service;

import com.taskengine.model.Task;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TaskQueueService {

    private final RedisTemplate<String, Object> redisTemplate;

    private static final String QUEUE_KEY = "task_queue";
    private static final String TASK_STORE = "task_store";

    public TaskQueueService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // Add task to queue + store
    public void addTask(Task task) {
        redisTemplate.opsForList().rightPush(QUEUE_KEY, task);
        redisTemplate.opsForHash().put(TASK_STORE, task.getId(), task);
    }

    // Blocking pop from queue
    public Task getTask() {
        Object task = redisTemplate.opsForList()
                .leftPop(QUEUE_KEY, 10, TimeUnit.SECONDS);

        return (Task) task;
    }

    // Get task status from Redis
    public Task getTaskById(String id) {
        return (Task) redisTemplate.opsForHash().get(TASK_STORE, id);
    }

    // Update task in Redis
    public void updateTask(Task task) {
        redisTemplate.opsForHash().put(TASK_STORE, task.getId(), task);
    }

    public void pushToQueue(Task task) {
        redisTemplate.opsForList().rightPush("task_queue", task);
    }
}