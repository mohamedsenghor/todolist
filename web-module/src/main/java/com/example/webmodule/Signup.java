package com.example.webmodule;

import com.example.ejbmodule.bean.UtilisateurRemote;
import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.EJB;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "signupServlet", value = "/signup")
public class Signup extends HttpServlet {
    @EJB
    private UtilisateurRemote utilisateurBean;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        //utilisateurBean.createUser("darcia", "passer");
        HttpSession session = request.getSession();
        RequestDispatcher dispatcher = null;

        System.out.println("calllll--->");
        Utilisateur e = new Utilisateur(request.getParameter("login"), request.getParameter("password"));
        int resp = utilisateurBean.createUser(e);
        if (resp == -1) {
            session.setAttribute("status", "failure");
            session.setAttribute("message", "Login or password malformed !");
            dispatcher = request.getRequestDispatcher("signup.jsp");
        } else if (resp == -2) {
            session.setAttribute("status", "failure");
            session.setAttribute("message", "Login already taken !");
            dispatcher = request.getRequestDispatcher("signup.jsp");
        } else {
            session.setAttribute("status", "success");
            session.setAttribute("message", "Registration successfully !");
            dispatcher = request.getRequestDispatcher("login.jsp");
        }
        dispatcher.forward(request, response);
    }
}