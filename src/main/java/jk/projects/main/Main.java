package jk.projects.main;

import jk.projects.model.Account;
import jk.projects.model.ATM;
import jk.projects.model.Transaction;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        ATM theATM = ATM.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initalize ATM with cash: ");
        String givenAmt = scanner.next();
        while(!isInt(givenAmt)) {
            System.out.println("Please give a valid amount...");
            givenAmt = scanner.next();
        }

        theATM.init(Integer.parseInt(givenAmt));
        int accountBalance = (int)(Math.random() * ((100000 - 100) + 1)) + 100;
        Account account = new Account(accountBalance);

        boolean exit = false;
        while (!exit) {
                System.out.println("Select the type of action you want to make: (Press 1 for Withdrawal or Press 2 for Deposit or quit to exit)");
                String givenAction = scanner.next();
                if (givenAction.equals("quit") || givenAction.equals("q")) {
                    exit = true;
                }
                if (isInt(givenAction)) {
                    while (!(Integer.parseInt(givenAction) == 1 || Integer.parseInt(givenAction) == 2)) {
                        System.out.println("(Press 1 for Withdrawal or Press 2 for Deposit or quit to exit)");
                        givenAction = scanner.next();
                        if (givenAction.equals("quit") || givenAction.equals("q")) {
                            exit = true;
                            break;
                        }
                    }
                }
                if (givenAction.trim().equals("1")) {
                    System.out.println("Give the amount of money you want to withdraw: ");
                    String givenAmtToWithdraw = scanner.next();
                    if(givenAmtToWithdraw.equals("quit") || givenAction.equals("q")) {
                        exit = true;
                    }
                    while (!isInt(givenAmtToWithdraw) || !theATM.isAmtValid(Integer.parseInt(givenAmtToWithdraw))) {
                        System.out.println("Please give a valid amount...");
                        givenAmtToWithdraw = scanner.next();
                        if(givenAmtToWithdraw.equals("quit") || givenAction.equals("q")) {
                            exit = true;
                            break;
                        }
                    }
                    try {
                        if(!exit) {
                            //1. check ATM available cash + update
                            if (theATM.checkCashAvailabilityAndUpdateAmt(Integer.parseInt(givenAmtToWithdraw), Transaction.WITHDRAW)) {
                                //2. update account balance
                                account.createTransaction(Integer.parseInt(givenAmtToWithdraw), Transaction.WITHDRAW);
                            } else {
                                System.out.println("The ATM cash is not enough for this type of transaction or the combination of notes cannot cover the specific amount.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Your balance is not enough for this type of transaction. Please try to deposit some money first.");
                        exit = true;
                    }
                } else if (givenAction.trim().equals("2")){
                    System.out.println("Give the amount of money you want to deposit: ");
                    String givenAmtToDeposit = scanner.next();
                    if(givenAmtToDeposit.equals("quit") || givenAction.equals("q")) {
                        exit = true;
                    }
                    while (!isInt(givenAmtToDeposit) || !theATM.isAmtValid(Integer.parseInt(givenAmtToDeposit))) {
                        System.out.println("Please give a valid amount...");
                        givenAmtToDeposit = scanner.next();
                        if(givenAmtToDeposit.equals("quit") || givenAction.equals("q")) {
                            exit = true;
                            break;
                        }
                    }
                    try {
                        if (!exit) {
                            //1. update ATM cash
                            if (theATM.checkCashAvailabilityAndUpdateAmt(Integer.parseInt(givenAmtToDeposit), Transaction.DEPOSIT)) {
                                //2. update account balance
                                account.createTransaction(Integer.parseInt(givenAmtToDeposit), Transaction.DEPOSIT);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Something went wrong with the deposit. Please try again");
                        exit = true;
                    }
                } else {
                    if (!(givenAction.equals("quit") || givenAction.equals("q"))) {
                        System.out.println("You didn't enter a valid action.");
                        continue;
                    } else {
                        break;
                    }
                }

            }
        System.exit(1);
    }

    public static boolean isInt(String s) {
        try {
            Integer.parseInt(s);
        } catch  (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
