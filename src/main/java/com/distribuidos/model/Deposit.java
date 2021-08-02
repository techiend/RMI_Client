package com.distribuidos.model;

import java.util.Date;

public class Deposit extends Transaction{
    public Deposit() {
    }

    public Deposit(float amount, String desc, int sourceNumber, int destinationNumber) {
        super(amount, desc, sourceNumber, destinationNumber, "Deposit");
    }
}
