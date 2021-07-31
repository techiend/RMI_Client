package com.distribuidos.service;

import com.distribuidos.model.Deposit;
import com.distribuidos.model.Transaction;
import com.distribuidos.model.Transference;
import com.distribuidos.model.Withdrawal;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TransactionalService extends Remote {
    Integer doDeposit(int destino, float monto, String descrip) throws RemoteException;
    Transaction doWithdrawal(Withdrawal withdrawal) throws RemoteException;
    Transaction doTransference(Transference transference) throws RemoteException;
}
