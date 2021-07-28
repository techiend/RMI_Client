package com.distribuidos.model;

import java.util.Date;

public class Deposit extends Transaction{
    public Deposit(int id, float amount, Date date, String desc, int sourceNumber, int destinationNumber) {
        super(id, amount, date, desc, sourceNumber, destinationNumber, "Deposit");
    }
}
