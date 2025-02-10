package com.example.webmodule;

import com.example.ejbmodule.bean.interfaces.TaskRemote;
import com.example.ejbmodule.bean.interfaces.TodolistRemote;
import com.example.ejbmodule.entity.TaskEntity;
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
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "TodolistServlet", value = "/tasks/*")
public class TaskServlet extends HttpServlet {
    @EJB
    TodolistRemote todolistBean;
    @EJB
    TaskRemote taskBean;
    Gson gson = new Gson();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        String pathInfo = request.getPathInfo();
        String login = (String) session.getAttribute("login");

        if (pathInfo == null || pathInfo.equals("/")) {

            List<TodoListEntity> todolists = todolistBean.findAll(login);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            if (todolists == null) {
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().write(gson.toJson(null));
            } else {


                List<TaskEntity> tasks = todolists.stream()
                        .map(TodoListEntity::getTasks)
                        .reduce(new ArrayList<TaskEntity>(), (acc, list) -> {
                            acc.addAll(list);
                            return acc;
                        });
                response.getWriter().write(gson.toJson(tasks));
            }
        } else {
            try {
                Long id = Long.valueOf(pathInfo.substring(1));
                TaskEntity task = taskBean.findById(id);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(task));
            } catch (NumberFormatException e) {
                JsonResponse.addProperty("code", HttpServletResponse.SC_BAD_REQUEST);
                JsonResponse.addProperty("message", "This task doe not exist !");
                JsonResponse.addProperty("status", false);
                JsonResponse.add("errors", new JsonArray());
                response.getWriter().write(gson.toJson(JsonResponse));
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            JsonResponse.addProperty("code", HttpServletResponse.SC_UNAUTHORIZED);
            JsonResponse.addProperty("message", "Failed updating the todolist !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
            return;
        }
        response.getWriter().write(gson.toJson(todolist));
    }

    protected void doPatch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject JsonResponse = new JsonObject();
        String pathInfo = request.getPathInfo();
        if (pathInfo == null || !pathInfo.matches("^/\\d+/status$")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid URL format");
            return;
        }
        try {
            Long id = Long.valueOf(pathInfo.substring(1));
            TaskEntity task = taskBean.findById(id);
            if (task == null) {
                JsonResponse.addProperty("code", HttpServletResponse.SC_BAD_REQUEST);
                JsonResponse.addProperty("message", "The title is empty !");
                JsonResponse.addProperty("status", false);
                JsonResponse.add("errors", new JsonArray());
                response.getWriter().write(gson.toJson(JsonResponse));
            } else {
                boolean isDone = Boolean.parseBoolean(request.getParameter("isDone"));
                taskBean.updateStatus(id, isDone);
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(gson.toJson(task));
            }
        } catch (Exception e) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            JsonResponse.addProperty("message", "The title is empty !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
            response.getWriter().write(gson.toJson(JsonResponse));
        }
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JsonObject JsonResponse = new JsonObject();
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
            JsonResponse.addProperty("message", "The user is authenticated !");
            JsonResponse.addProperty("status", true);
            JsonResponse.add("errors", new JsonArray());
        }
        response.getWriter().write(gson.toJson(result));
    }

    private void sendJsonResponse(HttpServletResponse response, Object data) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(gson.toJson(data));
        out.flush();
    }
}
