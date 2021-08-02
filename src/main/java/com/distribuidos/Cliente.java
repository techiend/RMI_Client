package com.distribuidos;

import com.distribuidos.implementation.AccountOption;
import com.distribuidos.inOutObjects.Response;
import com.distribuidos.model.*;
import com.distribuidos.service.AccountService;
import com.distribuidos.service.TransactionalService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.MalformedURLException;
import java.rmi.ConnectException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Cliente {
    public static int maxrows = 40;
    public static final boolean test = false;

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
                    depositScreen(accountService, transactionalService, user);
                    break;
                case 3:
                    withdrawalScreen(accountService, transactionalService, user);
                    break;
                case 4:
                    transferenceScreen(accountService, transactionalService, user);
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

    private static void withdrawalScreen(AccountService accountService, TransactionalService transactionalService, User user) {


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
                            doWithdrawal(transactionalService, user.getAccounts().get(0).getNumber());
                            enterWait();
                            break;
                        }
                    case 2:
                        if (user.getAccounts().size() >= 2) {
                            doWithdrawal(transactionalService, user.getAccounts().get(1).getNumber());
                            enterWait();
                            break;
                        }
                    case 3:
                        if (user.getAccounts().size() >= 3) {
                            doWithdrawal(transactionalService, user.getAccounts().get(2).getNumber());
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

    public static void showAccount(Account account){
        System.out.println("\nCuenta #"+account.getNumber());
        System.out.println("Saldo disponible: "+account.getCurrent_balance());
        System.out.println("\nUltimas transacciones:");
        System.out.println("------------------------------");
        for (Transaction trx :
                account.getTransactions()) {
            System.out.println("Transaccion #"+trx.getId());
            System.out.println("Monto: "+trx.getAmount());
            System.out.println("Tipo: "+trx.getType());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            System.out.println("Fecha: "+sdf.format(trx.getDate()));
            System.out.println("Origen: "+ ((trx.getSourceNumber() == 0)?" N/A": "#"+trx.getSourceNumber()));
            System.out.println("Destino: "+ ((trx.getDestinationNumber() == 0)?" N/A": "#"+trx.getDestinationNumber()));
            System.out.println("Descripcion: "+trx.getDesc());
            System.out.println("------------------------------");
        }
    }

    public static void accountsScreen(AccountService accountService, TransactionalService transactionalService, User user)
            throws MalformedURLException, NotBoundException, RemoteException
    {
        try {
            ObjectMapper mapper = new ObjectMapper();
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
                                showAccount(mapper.readValue(mapper.writeValueAsString(mapper.readValue(stringResponse, Response.class).getData()), Account.class));

                                enterWait();
                                break;
                            }
                        case 2:
                            if (user.getAccounts().size() >= 2) {
                                String stringResponse = accountService.getAccount(user.getAccounts().get(1).getNumber());
                                showAccount(mapper.readValue(mapper.writeValueAsString(mapper.readValue(stringResponse, Response.class).getData()), Account.class));
                                enterWait();
                                break;
                            }
                        case 3:
                            if (user.getAccounts().size() >= 3) {
                                String stringResponse = accountService.getAccount(user.getAccounts().get(2).getNumber());
                                showAccount(mapper.readValue(mapper.writeValueAsString(mapper.readValue(stringResponse, Response.class).getData()), Account.class));
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
            } else {
                System.out.println("\nNo posee cuentas para consultar...");
                enterWait();
            }
        }
        catch (Exception e){
            e.printStackTrace();
            System.out.println("Ha ocurrido un error, por favor intente mas tarde...");
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
        System.out.println("|"+print("Seleccione una cuenta: ")+"|");
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

    public static void depositSubMenu(User user){

        art();
        System.out.println(":----------------------------------------:");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("Seleccione una cuenta a depositar: ")+"|");
        System.out.println("|"+print("")+"|");
        for (int i = 0; i < user.getAccounts().size(); i++) {
            System.out.println("|"+print(String.format("%d. Cuenta #%d.",i+1, user.getAccounts().get(i).getNumber()))+"|");
        }
        System.out.println("|"+print("4. Cuenta de terceros.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("0. Atrás.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println(":----------------------------------------:");
        System.out.print("\n    Introduce una opcion: ");
    }
    
    public static void transferenceAcountOriginMenu(User user){
        art();
        System.out.println(":----------------------------------------:");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("Seleccione la cuenta origen: ")+"|");
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
    
    public static void transferenceAcountDestinationMenu(User user, int originAccount){

        art();
        System.out.println(":----------------------------------------:");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("Seleccione una cuenta a transferir: ")+"|");
        System.out.println("|"+print("")+"|");
        for (int i = 0; i < user.getAccounts().size(); i++) {
            if ( originAccount-1 != i ) {
            	System.out.println("|"+print(String.format("%d. Cuenta #%d.",user.getAccounts().get(i).getNumber(), user.getAccounts().get(i).getNumber()))+"|");
            }
        }
        System.out.println("|"+print(String.format("%d. Cuenta de terceros.", user.getAccounts().size()+1))+"|");
        System.out.println("|"+print("")+"|");
        System.out.println("|"+print("0. Atrás.")+"|");
        System.out.println("|"+print("")+"|");
        System.out.println(":----------------------------------------:");
        System.out.print("\n    Introduce una opcion: ");
    }
    
    public static String print(String text){
        return String.format("%-"+maxrows+"s", "    "+text).substring(0,maxrows);
    }

    public static void makeDeposit(AccountService accountService, TransactionalService transactionalService, int accountNumber) throws RemoteException {

        float monto = 0;
        Scanner scan = new Scanner(System.in);
        String descrip;

//                Validar monto introducido
        while (true) {
            scan = new Scanner(System.in);
            System.out.print("Introduce el monto a depositar: ");
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
        while (true) {
            scan = new Scanner(System.in);
            System.out.print("Descripcion: ");
            descrip = scan.next().trim();
            if (descrip.equals("")){
                System.out.println("Debe introducir una descripcion...");
            }
            else{
                break;
            }
        }

        Integer doDeposit = transactionalService.doDeposit(accountNumber, monto, descrip);

        if (doDeposit == 0)
            System.out.println("Transaccion fallida...");
        else
            System.out.println("Transaccion exitosa...");
    }

    public static void depositScreen(AccountService accountService, TransactionalService transactionalService, User user)
            throws MalformedURLException, NotBoundException, RemoteException
    {

        if (user.getAccounts().size() > 0) {
            boolean shutdown = true;
            while (shutdown) {
                clearScreen();
                depositSubMenu(user);
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();

                switch (option) {
                    case 1:
                        if (user.getAccounts().size() >= 1) {
                            makeDeposit(accountService,transactionalService, user.getAccounts().get(0).getNumber());
                            enterWait();
                            break;
                        }
                    case 2:
                        if (user.getAccounts().size() >= 2) {
                            makeDeposit(accountService,transactionalService, user.getAccounts().get(1).getNumber());
                            enterWait();
                            break;
                        }
                    case 3:
                        if (user.getAccounts().size() >= 3) {
                            makeDeposit(accountService,transactionalService, user.getAccounts().get(2).getNumber());

                            enterWait();
                            break;
                        }
                    case 4:
                        System.out.println("Cuenta de terceros... pedir datos");
                        enterWait();
                        shutdown = false;
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
        else {
            System.out.println("\nNo posee cuentas para consultar...");
            enterWait();
        }
    }
    
    public static void makeTransference(AccountService accountService, TransactionalService transactionalService, int originAccount, int destinationAccount) throws RemoteException {
    	
    	// Values for transference
    	float amount = 0;
        String description;
        Scanner scan = new Scanner(System.in);
        
    	while (true) {
            System.out.print("Introduce el monto a transferir: ");
            try {
                amount = scan.nextFloat();
                if (amount > 0)
                    break;
                else
                    System.out.println("Debe introducir un monto mayor a 0.");
            }
            catch (Exception e) {
                System.out.println("Introduce un monto valido con el formado: \"XXXX.XX\"...");
            }
        }
        
    	while (true) {
            System.out.print("Descripcion: ");
            description = scan.next().trim();
            if (description.equals("")){
                System.out.println("Debe introducir una descripcion...");
            }
            else{
                break;
            }
        }
    	
        
        Integer doTransference = transactionalService.doTransference( amount, description, originAccount, destinationAccount );

        if ( doTransference == 0) {
            System.out.println("Transaccion fallida...");
        } else if ( doTransference == -1) {
            System.out.println("Transaccion fallida, fondos insuficientes");
        } else {
            System.out.println("Transaccion exitosa...");
        }
    }
    
    public static void transferenceScreen(AccountService accountService, TransactionalService transactionalService, User user)
            throws MalformedURLException, NotBoundException, RemoteException
    {
    	
        if  (user.getAccounts().size() > 0) {
        	      
            boolean shutdown = true;
            while (shutdown) {
                
            	// Third party values
            	int thirdPartyAccount;
            	String thirdPartyId;
             	String responseThirdPartyId = "";
             	String thirdUserName = "";
            	
            	clearScreen();
                transferenceAcountOriginMenu(user);
                Scanner scan = new Scanner(System.in);
                int option = scan.nextInt();
                
                if( option == 0 ) {
                	enterWait();
                    shutdown = false;
                } else if (option > 0 && option <= user.getAccounts().size() ) {
                	
                	clearScreen();
                	transferenceAcountDestinationMenu(user,option);
                	int option_2 = scan.nextInt();
                	
                	if( option_2 == 0 ) {
                    	enterWait();
                        shutdown = false;
                    } else if (option_2 > 0 && option_2 <= user.getAccounts().size() && option != option_2 ) {
                    	makeTransference(accountService,transactionalService, user.getAccounts().get(option-1).getNumber(),user.getAccounts().get(option_2-1).getNumber() );
                    	shutdown = false;
                    } else if (option_2 == user.getAccounts().size()+1 ) {
                    	
                    	while (true) {
                            System.out.print("Introduce el numero de cuenta a transferir: ");
                            try {
                            	thirdPartyAccount = scan.nextInt();
                                if (thirdPartyAccount > 0)
                                    break;
                                else
                                    System.out.println("Debe introducir un numero superior a 0");
                            }
                            catch (Exception e) {
                                System.out.println("Introduce un numero valido");
                            }
                        }
                    	
                    	while (true) {
                            System.out.print("Introduce el documento de identidad asociado: ");
                            thirdPartyId = scan.next().trim();
                            if (thirdPartyId.equals("")) {
                                System.out.println("Debe introducir el documento de identidad...");
                            } else {
                                break;
                            }
                        }
                    	
                    	try {
                    		String string = accountService.getAccountUid( thirdPartyAccount );
                    		ObjectMapper mapper = new ObjectMapper();
                            Response response = mapper.readValue( string, Response.class);
                            if(response.getCod() == 0) {
                            	responseThirdPartyId = response.getData().toString();
                            }
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            break;
                        }
                    	
                    	try {
                    		String string = accountService.getUserName( thirdPartyId );
                    		ObjectMapper mapper = new ObjectMapper();
                            Response responseName = mapper.readValue( string, Response.class);
                            if(responseName.getCod() == 0) {
                            	thirdUserName = responseName.getData().toString();
                    		}
                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                            break;
                        }
                    	
                    	if ( thirdPartyId.equals( responseThirdPartyId ) ) {
                    		System.out.println(String.format("Los fondos seran transferidos a %s", thirdUserName));
                    		enterWait();
                    		makeTransference(accountService,transactionalService, user.getAccounts().get(option-1).getNumber(), thirdPartyAccount );
                        	shutdown = false;
                    	} else {
                    		System.out.println("\nEl usuario no coincide con la cuenta\no alguno de los campos esta equivocado...");
                    		shutdown = false;
                    		break;
                    	}
                    	
                    	
                    } else {
                    	System.out.println("\nLa opcion introducida no es correcta...");
                        enterWait();
                    }
                    
                } else {
                	System.out.println("\nLa opcion introducida no es correcta...");
                    enterWait();
                }
            }
        }
        else {
            System.out.println("\nNo posee cuentas para relizar transferencias");
            enterWait();
        }
    }

    public static void doWithdrawal(TransactionalService transactionalService, int account){

        try {
            float monto = 0;
            Scanner scan = new Scanner(System.in);

            while (true) {
                scan = new Scanner(System.in);
                System.out.print("Introduce el monto a retirar: ");
                try {
                    monto = scan.nextFloat();
                    if (monto > 0)
                        break;
                    else
                        System.out.println("Debe introducir un monto mayor a 0.");
                } catch (Exception e) {
                    System.out.println("Introduce un monto valido con el formado: \"XXXX.XX\"...");
                }
            }

            String stringResponse = transactionalService.doWithdrawal(monto, account);
//            System.out.println(stringResponse);

            ObjectMapper mapper = new ObjectMapper();
            Response authResponse = mapper.readValue(stringResponse, Response.class);

            if (authResponse.getCod() == 0) {
                System.out.println("\n"+authResponse.getMsg()+"\n");
                System.out.println("Monto disponible en su cuenta: " + authResponse.getData());
            } else {
                System.out.println("\n"+authResponse.getMsg()+"\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ha ocurrido un error, por favor intente mas tarde...");
        }

    }

}
