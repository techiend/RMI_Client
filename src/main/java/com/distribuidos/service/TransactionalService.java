package com.distribuidos.service;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TransactionalService extends Remote {
    Integer doDeposit(int destino, float monto, String descrip) throws RemoteException;
    String doWithdrawal(float amount, int cuentaOrigen) throws RemoteException;
    Integer doTransference( float amount, String description, int originAccount, int destinationAccount ) throws RemoteException;
}
