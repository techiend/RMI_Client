package com.distribuidos.model;

import java.util.List;

public class Account {
    int number;
    float current_balance;
    String user_id;
    List<Transaction> transactions;

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

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
