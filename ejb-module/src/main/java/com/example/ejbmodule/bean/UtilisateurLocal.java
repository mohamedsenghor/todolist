package com.example.ejbmodule.bean;

import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.Remote;

@Remote
public interface UtilisateurLocal extends  UtilisateurRemote {
    public boolean verificationParametre( String login , String password);
    public boolean verificationAlreadyExist( String login);
    public boolean verifyPassword(Utilisateur utilisateur, String password);
}
