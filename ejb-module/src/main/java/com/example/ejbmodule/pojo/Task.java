package com.example.ejbmodule.pojo;

import java.io.Serializable;
import java.util.Date;

public class Task implements Serializable {
    private Integer taskId;
    private String post;

    public Task() {
        super();
    }
    
    public Task(Integer taskId, String post) {
        this.taskId = taskId;
        this.post = post;
    }
    
    public Task(String post) {
        this.post = post;
    }

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }
}
