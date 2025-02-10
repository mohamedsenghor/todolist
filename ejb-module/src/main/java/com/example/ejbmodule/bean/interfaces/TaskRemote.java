package com.example.ejbmodule.bean.interfaces;

import com.example.ejbmodule.entity.TaskEntity;
import com.example.ejbmodule.entity.TodoListEntity;
import jakarta.ejb.Remote;

import java.util.List;

@Remote
public interface TaskRemote {
    List<TaskEntity> findAll(Long todolistId);
    TaskEntity findById(Long id);
    TaskEntity create(Long todoListId, String post);
    TaskEntity update(TaskEntity task);
    boolean updateStatus(Long id, boolean status);
    boolean deleteById(Long id);
    boolean delete(TaskEntity task);
}
