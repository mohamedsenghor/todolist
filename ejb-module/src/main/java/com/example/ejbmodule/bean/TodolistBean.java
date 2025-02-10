package com.example.ejbmodule.bean;

import com.example.ejbmodule.bean.interfaces.TodolistRemote;
import com.example.ejbmodule.dto.TaskDTO;
import com.example.ejbmodule.dto.TodoListDTO;
import com.example.ejbmodule.entity.TodoListEntity;
import com.example.ejbmodule.entity.UtilisateurEntity;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;
import java.util.stream.Collectors;

@Stateless(name = "TodolitEJB")
public class TodolistBean implements TodolistRemote {
    @PersistenceContext(unitName = "defal_pu")
    private EntityManager em;

    /**
     * @param login
     * @return
     */
    @Override
    public List<TodoListDTO> findAll(String login) {
        if (login == null) {
            return null;
        }
        List<TodoListEntity> todolists = em.createQuery("select t from TodoListEntity t where t.user.login = :login", TodoListEntity.class)
                .setParameter("login", login)
                .getResultList();
        return  todolists.stream()
                .map(todoList -> new TodoListDTO(
                        todoList.getTodolistId(),
                        todoList.getUser().getLogin(),
                        todoList.getTasks().stream()
                                .map(task -> new TaskDTO(task.getTaskId(), task.getPost(), task.isDone()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    /**
     * @param todolistId
     * @return
     */
    @Override
    public TodoListDTO findById(Long todolistId) {
        if (todolistId == null) return null;
        TodoListEntity todoList = em.find(TodoListEntity.class, todolistId);
        return new TodoListDTO(todolist);
    }

    /**
     * @param title
     * @return
     */
    @Override
    public TodoListEntity create(String login, String title) {
        if (title == null || title.isEmpty()) {
            return null;
        }
        UtilisateurEntity utilisateur = em.find(UtilisateurEntity.class, login);
        if (utilisateur == null) {
            return null;
        }
        TodoListEntity todoList = new TodoListEntity(title);
        todoList.setUser(utilisateur);
        em.persist(todoList);
        return todoList;
    }

    /**
     * @param todoList
     * @return
     */
    @Override
    public TodoListEntity update(TodoListEntity todoList, String title) {
        if (title == null || title.isEmpty()) {
            return null;
        }
        if (em.find(TodoListEntity.class, todoList.getTodolistId()) == null){
            return null;
        }
        todoList.setTitle(title);
        em.merge(todoList);
        return todoList;
    }

    /**
     * @param todoListId
     * @return
     */
    @Override
    public TodoListEntity updateById(Long todoListId, String title) {
        if (title == null || title.isEmpty() || todoListId == null) {
            return null;
        }
        TodoListEntity todoList = em.find(TodoListEntity.class, todoListId);
        if (todoList == null){
            return null;
        }
        todoList.setTitle(title);
        em.merge(todoList);
        return todoList;
    }

    /**
     * @param todolistId
     * @return
     */
    @Override
    public boolean markAsDone(Long todolistId) {
        return false;
    }

    /**
     * @param todoList
     * @return
     */
    @Override
    public boolean delete(TodoListEntity todoList) {
        if (todoList == null) return false;
        if (em.find(TodoListEntity.class, todoList.getTodolistId()) == null) {
            return false;
        }
        em.remove(todoList);
        return true;
    }

    /**
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Long id) {
        TodoListEntity todoList = em.find(TodoListEntity.class, id);
        if (todoList != null) {
            em.remove(todoList);
            return true;
        }
        return false;
    }
}
