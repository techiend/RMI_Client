package com.distribuidos.model;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Transaction {
    int id;
    float amount;
    String date;
    String desc;
    int sourceNumber;
    int destinationNumber;
    String type;

    public Transaction() {
    }

    public Transaction(float amount, Date date, String desc, int sourceNumber, int destinationNumber, String type) {

        this.amount = amount;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.date = sdf.format(date);
        this.desc = desc;
        this.sourceNumber = sourceNumber;
        this.destinationNumber = destinationNumber;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public void setDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        this.date = sdf.format(date);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getSourceNumber() {
        return sourceNumber;
    }

    public void setSourceNumber(int sourceNumber) {
        this.sourceNumber = sourceNumber;
    }

    public int getDestinationNumber() {
        return destinationNumber;
    }

    public void setDestinationNumber(int destinationNumber) {
        this.destinationNumber = destinationNumber;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
