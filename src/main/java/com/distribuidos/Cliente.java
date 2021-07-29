package com.distribuidos;

import com.distribuidos.implementation.AccountOption;
import com.distribuidos.model.User;
import com.distribuidos.service.AccountService;
import com.distribuidos.service.TransactionalService;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class Cliente {
    public static int maxrows = 40;
    public static final boolean test = true;

    public static void main(String[] args) {
        try {

            int connectionTries = 0;
            AccountService accountService = null;
            TransactionalService transactionalService = null;

            while (true) {
                try {
                    System.setProperty("java.rmi.server.hostname", "localhost");
                    accountService = (AccountService) Naming.lookup("//localhost:8808/account");
                    transactionalService = (TransactionalService) Naming.lookup("//localhost:8808/transactional");
                    break;
                } catch (Exception e) {
                    if (connectionTries <3) {
                        System.out.println("No se ha podido establecer comunicacion con el servidor...");
                        connectionTries += 1;
                        Thread.sleep(5000);
                    }
                    else {
                        break;
                    }
                }
            }

            if (connectionTries <3) {
                firstScreen(accountService, transactionalService);
            }
            else {
                System.out.println("No se pudo establecer comunicaicon con el servidor.");
                System.out.println("Por favor, comuniquese con el banco..");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void firstScreen(AccountService accountService, TransactionalService transactionalService)
            throws MalformedURLException, NotBoundException, RemoteException
    {

        boolean shutdown = true;
        while (shutdown) {
            try {
                clearScreen();
                menu();
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();

                switch (option) {
                    case 1:
//                        System.out.println("\nOpcion #1...");
                        AccountOption.doOpenAccount(accountService);
                        enterWait();
                        break;
                    case 2:
                        User user = AccountOption.doAuth(accountService);
                        if (user != null) {
                            secondScreen(accountService, transactionalService, user);
                        }
                        else
                            enterWait();
                        break;
                    case 0:
                        System.out.println("\nHasta luego.....");
                        enterWait();
                        shutdown = false;
                        break;
                    default:
                        System.out.println("\nLa opcion introducida no es correcta...");
                        enterWait();
                }
            } catch (ConnectException e) {
                System.out.println(e.getLocalizedMessage());
                accountService = (AccountService) Naming.lookup("//localhost:8808/account");
                transactionalService = (TransactionalService) Naming.lookup("//localhost:8808/transactional");
            }
        }
    }

    public static void secondScreen(AccountService accountService, TransactionalService transactionalService, User user)
            throws MalformedURLException, NotBoundException, RemoteException
    {

        boolean shutdown = true;
        while (shutdown) {
            clearScreen();
            subMenu(user.getName());
            Scanner scan = new Scanner(System.in);
            int option = scan.nextInt();

            switch (option) {
                case 1:
                    accountsScreen(accountService, transactionalService, user);
                    break;
                case 2:
                    System.out.println("\nOpcion #2...");
                    enterWait();
                    break;
                case 3:
                    System.out.println("\nOpcion #3...");
                    enterWait();
                    break;
                case 4:
                    System.out.println("\nOpcion #4...");
                    enterWait();
                    break;
                case 0:
                    enterWait();
                    shutdown = false;
                    break;
                default:
                    System.out.println("\nLa opcion introducida no es correcta...");
                    enterWait();
            }
        }
    }

    public static void accountsScreen(AccountService accountService, TransactionalService transactionalService, User user)
            throws MalformedURLException, NotBoundException, RemoteException
    {

        if (user.getAccounts().size() > 0) {
            boolean shutdown = true;
            while (shutdown) {
                clearScreen();
                accountSubMenu(user);
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();

                switch (option) {
                    case 1:
                        if (user.getAccounts().size() >= 1) {
                            String stringResponse = accountService.getAccount(user.getAccounts().get(0).getNumber());
                            System.out.println(stringResponse);
                            enterWait();
                            break;
                        }
                    case 2:
                        if (user.getAccounts().size() >= 2) {
                            String stringResponse = accountService.getAccount(user.getAccounts().get(1).getNumber());
                            System.out.println(stringResponse);
                            enterWait();
                            break;
                        }
                    case 3:
                        if (user.getAccounts().size() >= 3) {
                            String stringResponse = accountService.getAccount(user.getAccounts().get(2).getNumber());
                            System.out.println(stringResponse);
                            enterWait();
                            break;
                        }
                    case 0:
                        enterWait();
                        shutdown = false;
                        break;
                    default:
                        System.out.println("\nLa opcion introducida no es correcta...");
                        enterWait();
                }
            }
        }
        else {
            System.out.println("\nNo posee cuentas para consultar...");
            enterWait();
        }
    }

    public static void clearScreen() {
        for (int i = 0; i < 30; i++) {
            System.out.println("");
        }
    }

    public static void enterWait(){
        System.out.println("\nPresiona enter para continuar...");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
    }

    public static void art(){
        System.out.println("   __  __ _       _   ____              _      ");
        System.out.println("  |  \\/  (_)     (_) |  _ \\            | |     ");
        System.out.println("  | \\  / |_ _ __  _  | |_) | __ _ _ __ | | __  ");
        System.out.println("  | |\\/| | | '_ \\| | |  _ < / _` | '_ \\| |/ /  ");
        System.out.println("  | |  | | | | | | | | |_) | (_| | | | |   <   ");
        System.out.println("  |_|  |_|_|_| |_|_| |____/ \\__,_|_| |_|_|\\_\\  ");
        System.out.println("                                               ");
        System.out.println("");
    }

    public static void menu(){
        art();
        System.out.println(":----------------------------------------:");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("1. Apertura de cuenta.")+"|");
        System.out.println("|"+print("2. Realizar transacción.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("0. Salir.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println(":----------------------------------------:");
        System.out.print("\n    Introduce una opcion: ");
    }

    public static void subMenu(String name){
        art();
        System.out.println(":----------------------------------------:");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("Bienvenid@, "+name)+"|");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("1. Consulta de cuenta.")+"|");
        System.out.println("|"+print("2. Deposito en cuenta.")+"|");
        System.out.println("|"+print("3. Retiro de cuenta.")+"|");
        System.out.println("|"+print("4. Transferencia entre cuentas.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("0. Atrás.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println(":----------------------------------------:");
        System.out.print("\n    Introduce una opcion: ");
    }

    public static void accountSubMenu(User user){
        art();
        System.out.println(":----------------------------------------:");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("Seleccione una cuenta a consultar: ")+"|");
        System.out.println("|"+print("")+"|");
        for (int i = 0; i < user.getAccounts().size(); i++) {
            System.out.println("|"+print(String.format("%d. Cuenta #%d.",i+1, user.getAccounts().get(i).getNumber()))+"|");
        }
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("0. Atrás.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println(":----------------------------------------:");
        System.out.print("\n    Introduce una opcion: ");
    }

    public static String print(String text){
        return String.format("%-"+maxrows+"s", "    "+text).substring(0,maxrows);
    }

}
