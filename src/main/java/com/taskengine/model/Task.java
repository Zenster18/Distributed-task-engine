package com.taskengine.model;

import java.io.Serializable;

public class Task implements Serializable {

    private String id;
    private String type;
    private String data;
    private String status;
    private int retryCount;

    // Required for Redis/Jackson
    public Task() {}

    public Task(String id, String type, String data) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.status = "PENDING";
        this.retryCount = 0;
    }

    public String getId() { return id; }
    public String getType() { return type; }
    public String getData() { return data; }
    public String getStatus() { return status; }
    public int getRetryCount() { return retryCount; }

    public void setStatus(String status) { this.status = status; }
    public void incrementRetry() { this.retryCount++; }
}
