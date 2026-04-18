package com.taskengine.controller;

import com.taskengine.model.Task;
import com.taskengine.service.TaskQueueService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/task")
public class TaskController {
    private final TaskQueueService queueService;
    public TaskController(TaskQueueService queueService){
        this.queueService=queueService;
    }

    @PostMapping
    public String createTask(@RequestParam String data){
        String id= UUID.randomUUID().toString();
        Task task= new Task(id,data);
        queueService.addTask(task);
        return "Task submitted with id: "+id;
    }

    @GetMapping("/{id}")
    public Task getTask(@PathVariable String id){
        return queueService.getTaskById(id);
    }
}
