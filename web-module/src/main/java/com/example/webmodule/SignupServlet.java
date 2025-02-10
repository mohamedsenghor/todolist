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

import java.io.IOException;

@WebServlet(name = "signupServlet", value = "/signup")
public class SignupServlet extends HttpServlet {
    @EJB
    private UtilisateurRemote utilisateurBean;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        JsonObject JsonResponse = new JsonObject();
        Gson gson = new Gson();

        Utilisateur e = new Utilisateur(request.getParameter("login"), request.getParameter("password"));
        int resp = utilisateurBean.createUser(e);
        if (resp == -1) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_BAD_REQUEST);
            JsonResponse.addProperty("message", "LoginServlet or password incorrect(s)");
            JsonResponse.addProperty("status", true);
            JsonResponse.add("errors", new JsonArray());
        } else if (resp == -2) {
            JsonResponse.addProperty("code", HttpServletResponse.SC_CONFLICT);
            JsonResponse.addProperty("message", "LoginServlet already taken !");
            JsonResponse.addProperty("status", false);
            JsonResponse.add("errors", new JsonArray());
        } else {
            JsonResponse.addProperty("code", HttpServletResponse.SC_CREATED);
            JsonResponse.addProperty("message", "You successfully signed up");
            JsonResponse.addProperty("status", true);
            JsonResponse.add("errors", new JsonArray());
        }
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        String jsonData = gson.toJson(JsonResponse);
        response.getWriter().write(jsonData);
    }
}
