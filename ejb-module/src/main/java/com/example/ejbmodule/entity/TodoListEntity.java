package com.example.ejbmodule.entity;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.List;

@Entity
@Table(name = "todolists")
public class TodoListEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "todolist_id")
    private Long todolistId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    public TodoListEntity(String title) {
        this.title = title;
    }

    public TodoListEntity() {}

    @ManyToOne
    @JoinColumn(name = "login") // Foreign key in todolists table
    private UtilisateurEntity user;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true) // Todolist owns tasks
    private List<TaskEntity> tasks;

    public Long getTodolistId() {
        return todolistId;
    }

    public void setTodolistId(Long todolistId) {
        this.todolistId = todolistId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UtilisateurEntity getUser() {
        return user;
    }

    public void setUser(UtilisateurEntity user) {
        this.user = user;
    }

    public List<TaskEntity> getTasks() {
        return tasks;
    }

    public void setTasks(List<TaskEntity> tasks) {
        this.tasks = tasks;
    }
}
