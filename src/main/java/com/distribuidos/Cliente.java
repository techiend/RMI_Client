package com.distribuidos;

import com.distribuidos.implementation.AccountOption;
import java.util.Scanner;

public class Cliente {
    public static int maxrows = 40;
    public static final boolean test = true;

    public static void main(String[] args) {
        try {

            boolean shutdown = true;

            System.setProperty("java.rmi.server.hostname", "localhost");
//            AccountService accountService = (AccountService) Naming.lookup("//localhost:8808/account");
//            TransactionalService transactionalService = (TransactionalService) Naming.lookup("//localhost:8808/transactional");

            while (shutdown){

                clearScreen();
                menu();
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();

                switch (option){
                    case 1:
//                        System.out.println("\nOpcion #1...");
                        AccountOption.doOpenAccount();
                        enterWait();
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
                    case 5:
                        System.out.println("\nOpcion #5...");
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

            }
        }
        catch (Exception e) {
            e.printStackTrace();
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

    public static void menu(){
        System.out.println(":----------------------------------------:");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("1. Apertura de cuenta.")+"|");
        System.out.println("|"+print("2. Consulta de cuenta.")+"|");
        System.out.println("|"+print("3. Deposito en cuenta.")+"|");
        System.out.println("|"+print("4. Retiro de cuenta.")+"|");
        System.out.println("|"+print("5. Transferencia entre cuentas.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("0. Salir.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println(":----------------------------------------:");
        System.out.print("\n    Introduce una opcion: ");
    }

    public static String print(String text){
        return String.format("%-"+maxrows+"s", "    "+text).substring(0,maxrows);
    }

}
