package com.distribuidos.model;

import java.util.Date;

public class Withdrawal extends Transaction{
    public Withdrawal(float amount, Date date, String desc, int sourceNumber, int destinationNumber) {
        super(amount, date, desc, sourceNumber, destinationNumber, "Withdrawal");
    }
}
