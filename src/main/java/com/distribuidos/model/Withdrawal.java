package com.distribuidos.model;

import java.util.Date;

public class Withdrawal extends Transaction{
    public Withdrawal() {
    }

    public Withdrawal(float amount, String desc, int sourceNumber, int destinationNumber) {
        super(amount, desc, sourceNumber, destinationNumber, "Withdrawal");
    }
}
