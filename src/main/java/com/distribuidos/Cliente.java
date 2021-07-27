package com.distribuidos;

import java.rmi.Naming;

public class Cliente {
    public static void main(String[] args) {
        try {

            EchoService estoNoEsUnaVariable = (EchoService) Naming.lookup("//localhost:8808/echo");
            System.out.println(estoNoEsUnaVariable.echo("Mensaje Echo"));
        }
        catch (Exception e) {
            System.err.println("Excepcion en cliente:");
            e.printStackTrace();
        }
    }
}
