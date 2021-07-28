package com.distribuidos.model;

public class Account {
    int number;
    float current_balance;
    String user_id;

    public Account() {
    }

    public Account(String user_id) {}

    public int getNumber() {
        return number;
    }

    public float getCurrent_balance() {
        return current_balance;
    }

    public String getUser_id() {
        return user_id;
    }
}
