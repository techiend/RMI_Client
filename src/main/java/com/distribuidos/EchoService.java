package com.distribuidos;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface EchoService extends Remote {
    String echo (String s) throws RemoteException;
}
