package com.example.ejbmodule.pojo;

import java.io.Serializable;

public class Utilisateur  implements Serializable {
    String login ;
    String password;

    public  Utilisateur(String login , String password){
        this.login=login;
        this.password=password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
