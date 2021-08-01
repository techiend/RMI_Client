package com.distribuidos.model;

import java.util.Date;

public class Transference extends Transaction {
    	
	public Transference(float amount, Date date, String desc, int sourceNumber, int destinationNumber) {
        super(amount, date, desc, sourceNumber, destinationNumber, "Transference");
    }
	
}
