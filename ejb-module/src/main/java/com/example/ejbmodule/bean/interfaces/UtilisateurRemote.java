package com.example.ejbmodule.bean.interfaces;

import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.Remote;

@Remote
public interface UtilisateurRemote {

    int createUser(String login, String password);
    int createUser(Utilisateur user);
    boolean authenticate(String login, String password);
}
