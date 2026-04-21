package com.taskengine.controller;

import com.taskengine.model.Task;
import com.taskengine.service.TaskQueueService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {

    private final TaskQueueService queueService;

    public TaskController(TaskQueueService queueService) {
        this.queueService = queueService;
    }

    @PostMapping
    public Task createTask(@RequestParam String type,
                           @RequestParam String data) {

        Task task = new Task(
                UUID.randomUUID().toString(),
                type,
                data
        );

        queueService.addTask(task);
        return task;
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable String id) {
        return queueService.getTaskById(id);
    }
}
