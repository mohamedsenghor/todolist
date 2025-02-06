package com.example.ejbmodule.bean;

import com.example.ejbmodule.entity.UtilisateurEntity;
import com.example.ejbmodule.pojo.Utilisateur;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Stateless(name = "UtilisateurEJB")
public class UtilisateurBean implements UtilisateurRemote, UtilisateurLocal {

    @PersistenceContext(unitName = "defal_pu")
    private EntityManager entityManager;

    public UtilisateurBean() {
    }

    public int createUser(String login, String password) {
        if (!login.isEmpty() && !password.isEmpty()) {
            return -1;
        }

        UtilisateurEntity userEntity = entityManager.find(UtilisateurEntity.class, login);
        if (userEntity != null) {
            return -2;
        }

        userEntity = new UtilisateurEntity();
        userEntity.setLogin(login);
        userEntity.setPassword(password);
        entityManager.persist(userEntity);
        return 0;

    }

    public int createUser(Utilisateur user) {
        System.out.println(" ejb createUser" + user.getLogin().length() + user.getPassword().length());
        if (!this.verificationParametre(user.getLogin(), user.getPassword())) {
            return -1;
        }

        if (!this.verificationAlreadyExist(user.getLogin())) {
            return -2;
        }
        System.out.println(" validation createUser");

        UtilisateurEntity userEntity = new UtilisateurEntity();
        userEntity.setLogin(user.getLogin());
        userEntity.setPassword(user.getPassword());
        entityManager.persist(userEntity);
        System.out.println(" apres creation");
        return 0;
    }

    @Override
    public boolean verificationParametre(String login, String password) {

        if (login.isEmpty() || password.isEmpty()) {
            return false;
        }
        System.out.println(" validation des donnees ");
        return true;
    }

    @Override
    public boolean verificationAlreadyExist(String login) {
        UtilisateurEntity userEntity = entityManager.find(UtilisateurEntity.class, login);
        System.out.println("userEntity....>" + userEntity);
        return userEntity == null;
    }

    @Override
    public boolean verifyPassword(Utilisateur user, String password) {
        System.out.println(" validation des donnees ");
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public boolean authenticate(String login, String password) {
        UtilisateurEntity user = entityManager.find(UtilisateurEntity.class, login);
        if (user == null) {
            return false;
        }
        return user.getPassword().equals(password);
    }
}
