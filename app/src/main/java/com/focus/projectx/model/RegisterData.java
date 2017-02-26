package com.focus.projectx.model;

/**
 * Created by Focus on 26.02.2017.
 */

public class RegisterData {
    private String name;
    private String mail;
    private String password;
    private String description;

    public RegisterData(String name, String mail, String password, String description) {
        this.name = name;
        this.mail = mail;
        this.password = password;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
