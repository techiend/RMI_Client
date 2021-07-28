package com.distribuidos.implementation;

import com.distribuidos.Cliente;
import com.distribuidos.inOutObjects.OpenAccountOutput;
import com.distribuidos.inOutObjects.Response;
import com.distribuidos.model.Account;
import com.distribuidos.model.User;
import com.distribuidos.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Console;
import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class AccountOption {

    public static void doOpenAccount(AccountService accountService) throws ConnectException {

        try {
//            AccountService accountService = (AccountService) Naming.lookup("//localhost:8808/account");

            boolean openAccount = false;
            User user = null;

            Scanner scan = new Scanner(System.in);
            System.out.println("\nOpcion: Apertura de cuenta...\n");

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
                    user = AccountOption.doAuth(accountService);
                    if (user != null) {
//                        System.out.println(user.getName());
                        openAccount = true;
                    }

                } else {
                    System.out.println("Lo sentimos, ya posee el máximo número de cuentas abiertas...");
                }

            }
            else if (checkResponse.getCod() == 1) {
                System.out.println(checkResponse.getMsg());
                user = doCreateUser(accountService, documentoID);
                if (user != null) {
                    openAccount = true;
                }
            }
            else {
                System.out.println(checkResponse.getMsg());
            }

            if (openAccount){

                System.out.println("Bienvenid@, "+user.getName()+"...\n");
                float monto = 0;
//                Validar monto introducido
                while (true) {
                    scan = new Scanner(System.in);
                    System.out.println("Introduce el monto inicial con el que deseas abrir la cuenta: ");
                    try {
                        monto = scan.nextFloat();
                        if (monto > 0)
                            break;
                        else
                            System.out.println("Debe introducir un monto mayor a 0.");
                    }
                    catch (Exception e) {
                        System.out.println("Introduce un monto valido con el formado: \"XXXX.XX\"...");
                    }
                }


                Response createAccResponse = mapper.readValue(accountService.createAccount(user.getDocument_id(), monto), Response.class);

                if (createAccResponse.getCod() == 0){
                    System.out.println(createAccResponse.getMsg());
                    Account account =  mapper.readValue(mapper.writeValueAsString(createAccResponse.getData()),Account.class);
                    System.out.println("Su nuevo numero de cuenta es: " + account.getNumber());
                }
                else {
                    System.out.println(createAccResponse.getMsg());
                }
            }

        }
        catch (ConnectException e){
            throw new ConnectException("Se ha perdido la conexion, reconectando...");
        }
        catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static User doAuth(AccountService accountService){

        try {

//            AccountService accountService = (AccountService) Naming.lookup("//localhost:8808/account");

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
                System.out.println("\n"+authResponse.getMsg()+"\n");
                return  mapper.readValue(mapper.writeValueAsString(authResponse.getData()),User.class);
            } else {
                System.out.println("\n"+authResponse.getMsg()+"\n");
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static User doCreateUser(AccountService accountService, String document_id){

        String nombre = null, usuario = null, clave = null;

        try {

            Scanner scan = new Scanner(System.in);
            System.out.println("\nRegistro de nuevo usuario...\n");

//            Introducir Nombre diferente a vacio
            while (true) {
                scan = new Scanner(System.in);
                System.out.print("Nombre: ");
                nombre = scan.next().trim();
                if (nombre.equals("")){
                    System.out.println("Debe introducir un nombre valido...");
                }
                else{
                    break;
                }
            }
//            Introducir Usuario diferente a vacio
            while (true) {
                scan = new Scanner(System.in);
                System.out.print("Usuario: ");
                usuario = scan.next().trim();
                if (usuario.equals("")){
                    System.out.println("Debe introducir un usuario valido...");
                }
                else{
                    break;
                }
            }
//            Introducir Clave diferente a vacio
            while (true) {
                scan = new Scanner(System.in);
                if (Cliente.test) {
                    System.out.print("Clave: ");
                    clave = scan.next().trim();
                } else {
                    Console console = System.console();
                    char[] passwordArray = console.readPassword("Clave: ");
                    clave = new String(passwordArray).trim();
                }
                if (clave.equals("")){
                    System.out.println("Debe introducir una clave valida...");
                }
                else{
                    break;
                }
            }

            User newUser = new User();
            newUser.setDocument_id(document_id);
            newUser.setName(nombre);
            newUser.setUsername(usuario);
            newUser.setPassword(clave);

            ObjectMapper mapper = new ObjectMapper();

            String stringResponse = accountService.createUser(mapper.writeValueAsString(newUser));
            System.out.println(stringResponse);

            Response createResponse = mapper.readValue(stringResponse, Response.class);
            if (createResponse.getCod() == 0) {
                System.out.println("\n"+createResponse.getMsg()+"\n");
                return  mapper.readValue(mapper.writeValueAsString(createResponse.getData()),User.class);
            }
            else {
                System.out.println("\n"+createResponse.getMsg()+"\n");
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
