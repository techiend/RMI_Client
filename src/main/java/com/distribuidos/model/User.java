package com.distribuidos.model;

public class User {
    String document_id;
    String name;
    String username;
    String password;

    public User() {
    }

    public User(String document_id, String name, String username, String password) {
        this.document_id = document_id;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public String getDocument_id() {
        return document_id;
    }

    public void setDocument_id(String document_id) {
        this.document_id = document_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
