package com.distribuidos.model;

import java.util.List;

public class User {
    String document_id;
    String name;
    String username;
    String password;
    List<Account> accounts;

    public User() {
    }

    public User(String document_id, String name, String username, String password, List<Account> accounts) {
        this.document_id = document_id;
        this.name = name;
        this.username = username;
        this.password = password;
        this.accounts = accounts;
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

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }
}
