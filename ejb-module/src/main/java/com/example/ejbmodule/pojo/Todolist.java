package com.example.ejbmodule.pojo;

import java.io.Serializable;

public class Todolist implements Serializable {
    private Long todolistId;
    private String title;
    
    public Todolist () {
        super();
    }
    
    public Todolist(Long todolistId, String title) {
        this.todolistId = todolistId;
        this.title = title;
    }
    
    public Todolist(String title) {
        this.title = title;
    }

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
}
