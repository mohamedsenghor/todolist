package com.example.ejbmodule.dto;

import com.example.ejbmodule.entity.TodoListEntity;

import java.util.List;

public class TodoListDTO {
    private Long todolistId;
    private String login;
    private List<TaskDTO> tasks;

    public TodoListDTO(Long todolistId, String login, List<TaskDTO> tasks) {
        this.todolistId = todolistId;
        this.login = login;
        this.tasks = tasks;
    }
    
    public TodoListDTO(TodoListEntity todoList) {
        this.todolistId = todoList.getTodolistId();
        this.tasks = todoList.getTasks().stream();
    }

    public Long getTodolistId() {
        return todolistId;
    }

    public String getUsername() {
        return login;
    }

    public List<TaskDTO> getTasks() {
        return tasks;
    }
}
