package com.example.ejbmodule.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "post")
    private String post;

    @Column(name = "is_done")
    private boolean isDone;

    @ManyToOne
    @JoinColumn(name = "todolist_id") // Foreign key in tasks table
    private TodoListEntity todoList;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public TodoListEntity getTodoList() {
        return todoList;
    }

    public void setTodoList(TodoListEntity todoList) {
        this.todoList = todoList;
    }
}