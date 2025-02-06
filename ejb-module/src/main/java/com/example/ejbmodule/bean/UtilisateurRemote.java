package com.example.ejbmodule.bean;

import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.Remote;

@Remote
public interface UtilisateurRemote {

    public int createUser(String login , String password);
    public int createUser(Utilisateur user);
    public boolean authenticate(String login, String password);
}
