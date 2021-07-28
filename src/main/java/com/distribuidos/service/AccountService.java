package com.distribuidos.service;

import com.distribuidos.model.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface AccountService extends Remote {
    String checkUser(String document_id) throws RemoteException;
    String validateUser(String username, String password) throws RemoteException;
    String createUser(User user) throws RemoteException;
    String createAccount(String document_id, float montoInicial) throws RemoteException;
    String getAccount(int number) throws RemoteException;
}
