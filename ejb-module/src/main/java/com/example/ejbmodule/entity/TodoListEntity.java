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

    @ManyToOne
    @JoinColumn(name = "user_id") // Foreign key in todolists table
    private UtilisateurEntity user;

    @OneToMany(mappedBy = "todoList", cascade = CascadeType.ALL, orphanRemoval = true) // Todolist owns tasks
    private List<TaskEntity> tasks;

    public Long getTodolistId() {
        return todolistId;
    }

    public void setTodolistId(Long todolistId) {
        this.todolistId = todolistId;
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
