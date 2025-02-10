package com.example.ejbmodule.bean.interfaces;

import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.Local;

@Local
public interface UtilisateurLocal extends  UtilisateurRemote {
    boolean verificationParametre(String login, String password);
    boolean verificationAlreadyExist(String login);
    boolean verifyPassword(Utilisateur utilisateur, String password);
}
