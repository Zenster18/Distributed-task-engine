package com.taskengine.model;

public class Task {
    private String id;
    private String data;
    private String status;
    private int retryCount;

    public Task(String id, String data){
        this.id=id;
        this.data=data;
        this.status="PENDING";
        this.retryCount=0;
    }

    public String getId(){
        return id;
    }

    public String getData(){
        return data;
    }

    public String getStatus(){
        return status;
    }

    public int getRetryCount(){
        return retryCount;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public void incrementRetry(){
        this.retryCount++;
    }
}
