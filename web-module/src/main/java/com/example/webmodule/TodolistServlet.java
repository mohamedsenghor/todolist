package com.example.webmodule;

import com.example.ejbmodule.bean.interfaces.TodolistRemote;
import com.example.ejbmodule.entity.TodoListEntity;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.ejb.EJB;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet(name = "TodolistServlet", value = "/todolists/*")
public class TodolistServlet extends HttpServlet {
    @EJB
    TodolistRemote todolistBean;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject JsonResponse = new JsonObject();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_UNAUTHORIZED);
            JsonResponse.addProperty("message", "The user is not authenticated !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
            return;
        }

        String login = (String) session.getAttribute("login");
        List<TodoListEntity> todolists = todolistBean.findAll(login);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(todolists));
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Gson gson = new Gson();
        JsonObject JsonResponse = new JsonObject();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_UNAUTHORIZED);
            JsonResponse.addProperty("message", "The user is not authenticated !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
            return;
        }

        String login = (String) session.getAttribute("login");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        String title = request.getParameter("title");
        if (title.isEmpty()) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_BAD_REQUEST);
            JsonResponse.addProperty("message", "The title is empty !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
        } else {
            TodoListEntity todoList = todolistBean.create(login, title);
            response.getWriter().write(gson.toJson(todoList));
        }
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        JsonObject JsonResponse = new JsonObject();

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_UNAUTHORIZED);
            JsonResponse.addProperty("message", "The user is not authenticated !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
            return;
        }

        String login = (String) session.getAttribute("login");
        Long todolistId = Long.valueOf(request.getParameter("todolistId"));
        String title = request.getParameter("title");

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        TodoListEntity todolist = todolistBean.updateById(todolistId, title);
        if (todolist == null) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_NOT_FOUND);
            JsonResponse.addProperty("message", "Todolist not found");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
            return;
        }
        if (!Objects.equals(todolist.getUser().getLogin(), login)) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_UNAUTHORIZED);
            JsonResponse.addProperty("message", "You are not the owner of this todolist !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
            return;
        }
        response.getWriter().write(gson.toJson(todolist));
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject JsonResponse = new JsonObject();
        Gson gson = new Gson();
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("login") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        String login = (String) session.getAttribute("login");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        Long todolistId = Long.valueOf(request.getParameter("todolistId"));
        boolean result = todolistBean.deleteById(todolistId);
        if (!result) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_NOT_FOUND);
            JsonResponse.addProperty("message", "Failed updating the todolist !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
        } else {
            JsonResponse.addProperty("code", HttpServletResponse.SC_OK);
            JsonResponse.addProperty("message", "The task is deleted !");
            JsonResponse.addProperty("status", true);
            JsonResponse.add("errors", new JsonArray());
        }
        response.getWriter().write(gson.toJson(JsonResponse));
    }
}
