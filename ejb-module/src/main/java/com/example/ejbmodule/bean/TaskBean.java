package com.example.ejbmodule.bean;

import com.example.ejbmodule.bean.interfaces.TaskRemote;
import com.example.ejbmodule.entity.TaskEntity;
import com.example.ejbmodule.entity.TodoListEntity;
import jakarta.ejb.Stateful;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

@Stateful(name = "TaskEJB")
public class TaskBean implements TaskRemote {
    @PersistenceContext(unitName = "defal_pu")
    private EntityManager em;
    
    /**
     * @param todolistId
     * @return
     */
    @Override
    public List<TaskEntity> findAll(Long todolistId) {
        return em.createQuery("SELECT t  FROM TaskEntity t WHERE t.todoList.todolistId = :todolistId", TaskEntity.class)
                .setParameter("todolistId", todolistId)
                .getResultList();
    }

    /**
     * @param id
     * @return
     */
    @Override
    public TaskEntity findById(Long id) {
        return em.find(TaskEntity.class, id);
    }

    /**
     * @param todoListId
     * @param post
     * @return
     */
    @Override
    public TaskEntity create(Long todoListId, String post) {
        TodoListEntity todoList = em.find(TodoListEntity.class, todoListId);
        if (todoList == null) return null;

        TaskEntity task = new TaskEntity(post, false);
        task.setTodoList(todoList);
        em.persist(task);
        return task;
    }

    /**
     * @param task
     * @return
     */
    @Override
    public TaskEntity update(TaskEntity task) {
        if (task == null) return null;
        if (em.find(TaskEntity.class, task.getTaskId()) == null) return null;
        return em.merge(task);
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean updateStatus(Long id, boolean status) {
        if (id == null) return false;
        TaskEntity task = em.find(TaskEntity.class, id);
        if  (task == null) return false;
        em.merge(task);
        return true;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Long id) {
        if (id == null) return false;
        TaskEntity task = em.find(TaskEntity.class, id);
        if (task == null) return false;
        em.remove(task);
        return true;
    }

    /**
     * @param task
     * @return
     */
    @Override
    public boolean delete(TaskEntity task) {
        if ((task == null) || (task.getTaskId() == null)) return false;
        if (em.find(TaskEntity.class, task.getTaskId()) == null) return false;
        em.remove(task);
        return false;
    }
}
