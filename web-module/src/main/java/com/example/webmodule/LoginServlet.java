package com.example.webmodule;

import com.example.ejbmodule.bean.interfaces.UtilisateurRemote;
import com.example.ejbmodule.pojo.Utilisateur;
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

@WebServlet(name = "loginServlet", value = "/login")
public class LoginServlet extends HttpServlet {
    @EJB
    private UtilisateurRemote utilisateurBean;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        session.setMaxInactiveInterval(1200);

        System.out.println("LoginServlet Servlet Called!");

        System.out.println("LoginServlet: " + request.getParameter("login"));
        System.out.println("Password: " + request.getParameter("password"));

        if (session.getAttribute("login") != null && session.getAttribute("login").equals(request.getParameter("login"))) {
            response.sendRedirect("/index.jsp");
            return;
        }
        if (utilisateurBean == null) {
            throw new ServletException("Utilisateur EJB not injected properly");
        }
        Utilisateur e = new Utilisateur(request.getParameter("login"), request.getParameter("password"));
        if (utilisateurBean == null) {
            System.out.println("utilisateurBean EJB not injected properly!");
            throw new ServletException("Utilisateur EJB not injected properly");
        }
        boolean resp = utilisateurBean.authenticate(e.getLogin(), e.getPassword());
        JsonObject JsonResponse = new JsonObject();
        Gson gson = new Gson();

        if (resp) {
            session.setAttribute("login", e.getLogin());

            System.out.println("The user is authenticated !");

            JsonResponse.addProperty("code", HttpServletResponse.SC_OK);
            JsonResponse.addProperty("message", "The user is authenticated !");
            JsonResponse.addProperty("status", true);
            JsonResponse.add("errors", new JsonArray());
        } else {
            JsonResponse.addProperty("code", HttpServletResponse.SC_UNAUTHORIZED);
            JsonResponse.addProperty("message", "The user is not authenticated !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonData = gson.toJson(JsonResponse);
        response.getWriter().write(jsonData);
    }
}