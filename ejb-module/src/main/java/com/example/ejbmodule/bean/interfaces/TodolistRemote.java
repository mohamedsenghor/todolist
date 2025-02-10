package com.example.ejbmodule.bean.interfaces;

import com.example.ejbmodule.dto.TodoListDTO;
import com.example.ejbmodule.entity.TaskEntity;
import com.example.ejbmodule.entity.TodoListEntity;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TodolistRemote {
    List<TodoListDTO> findAll(String login);
    TodoListDTO findById(Long todolistId);
    TodoListDTO create(String login,String title);
    TodoListDTO update(TodoListEntity todoList, String title);
    TodoListDTO updateById(Long todoListId, String title);
    boolean markAsDone(Long todolistId);
    boolean delete(TodoListDTO todoList);
    boolean deleteById(Long id);
}
