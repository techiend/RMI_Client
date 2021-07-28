package com.distribuidos.implementation;

import com.distribuidos.Cliente;
import com.distribuidos.inOutObjects.OpenAccountOutput;
import com.distribuidos.inOutObjects.Response;
import com.distribuidos.model.User;
import com.distribuidos.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Console;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

public class AccountOption {

    public static void doOpenAccount(){

        try {
            AccountService accountService = (AccountService) Naming.lookup("//localhost:8808/account");

            Scanner scan = new Scanner(System.in);
            System.out.println("Opcion: Apertura de cuenta...");

            System.out.print("Por favor, introducir su documento de identidad: ");
            String documentoID = scan.next();

            String stringResponse = accountService.checkUser(documentoID);
//            System.out.println(stringResponse);

            ObjectMapper mapper = new ObjectMapper();
            Response checkResponse = mapper.readValue(stringResponse, Response.class);

            if (checkResponse.getCod() == 0) {

                String openAccountString = mapper.writeValueAsString(checkResponse.getData());
                OpenAccountOutput data = mapper.readValue(openAccountString,OpenAccountOutput.class);;

                if (data.getCantAccounts() < 3) {
//                    Validar credenciales
                    User user = AccountOption.doAuth();
                    System.out.println(user.getName());

                } else {
                    System.out.println("Lo sentimos, ya posee el máximo número de cuentas abiertas...");
                }

            } else if (checkResponse.getCod() == 1) {
                System.out.println(checkResponse.getMsg());
            } else {
                System.out.println(checkResponse.getMsg());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static User doAuth(){

        try {

            AccountService accountService = (AccountService) Naming.lookup("//localhost:8808/account");

            Scanner scan = new Scanner(System.in);
            System.out.println("\nPor favor, autentifiquese...\n");
            System.out.print("Usuario: ");
            String usuario = scan.next();
            String clave = "";
            if (Cliente.test) {
                System.out.print("Clave: ");
                clave = scan.next();
            } else {
                Console console = System.console();
                char[] passwordArray = console.readPassword("Clave: ");
                clave = new String(passwordArray);
            }

            String stringResponse = accountService.validateUser(usuario, clave);
//            System.out.println(stringResponse);

            ObjectMapper mapper = new ObjectMapper();
            Response authResponse = mapper.readValue(stringResponse, Response.class);
            if (authResponse.getCod() == 0) {
                System.out.println(authResponse.getMsg());
                return  mapper.readValue(mapper.writeValueAsString(authResponse.getData()),User.class);
            } else {
                System.out.println(authResponse.getMsg());
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
