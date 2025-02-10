package com.example.ejbmodule.dto;

public class TaskDTO {
    private Long taskId;
    private String post;
    private boolean isDone;

    public TaskDTO(Long taskId, String post, boolean isDone) {
        this.taskId = taskId;
        this.post = post;
        this.isDone = isDone;
    }

    public Long getTaskId() {
        return taskId;
    }

    public String getPost() {
        return post;
    }

    public boolean isDone() {
        return isDone;
    }
}
