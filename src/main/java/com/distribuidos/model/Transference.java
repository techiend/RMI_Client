package com.distribuidos.model;

import java.util.Date;

public class Transference extends Transaction {
    public Transference() {
    }

    public Transference(float amount, String desc, int sourceNumber, int destinationNumber) {
        super(amount, desc, sourceNumber, destinationNumber, "Transference");
    }
}
