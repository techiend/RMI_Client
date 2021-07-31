package com.distribuidos.model;

import java.util.Date;

public class Deposit extends Transaction{
    public Deposit(float amount, Date date, String desc, int sourceNumber, int destinationNumber) {
        super( amount, date, desc, sourceNumber, destinationNumber, "Deposit");
    }
}
