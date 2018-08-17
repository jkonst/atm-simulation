package jk.projects.main;

import jk.projects.model.Account;
import jk.projects.model.MachineATM;
import jk.projects.model.Transaction;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        MachineATM theATM = MachineATM.getInstance();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Initalize ATM with cash: ");
        String givenAmt = scanner.next();
        while(!isDouble(givenAmt)) {
            System.out.println("Please give a valid amount...");
            givenAmt = scanner.next();
        }

        theATM.init(Double.parseDouble(givenAmt));
        Account account = new Account(9288);

        boolean exit = false;
        while (!exit) {
                System.out.println("Select the type of action you want to make: (Press 1 for Withdrawal or Press 2 for Deposit or quit to exit)");
                String givenAction = scanner.next();
                if (givenAction.equals("quit")) {
                    exit = true;
                }
                if (isInt(givenAction)) {
                    while (!(Integer.parseInt(givenAction) == 1 || Integer.parseInt(givenAction) == 2)) {
                        System.out.println("(Press 1 for Withdrawal or Press 2 for Deposit or quit to exit)");
                        givenAction = scanner.next();
                        if (givenAction.equals("quit")) {
                            exit = true;
                            break;
                        }
                    }
                }
                if (givenAction.trim().equals("1")) {
                    System.out.println("Give the amount of money you want to withdraw: ");
                    String givenAmtToWithdraw = scanner.next();
                    if(givenAmtToWithdraw.equals("quit")) {
                        exit = true;
                    }
                    while (!isDouble(givenAmtToWithdraw) || !theATM.isAmtValid(Double.parseDouble(givenAmtToWithdraw))) {
                        System.out.println("Please give a valid amount...");
                        givenAmtToWithdraw = scanner.next();
                        if(givenAmtToWithdraw.equals("quit")) {
                            exit = true;
                            break;
                        }
                    }
                    try {
                        if(!exit) {
                            //1. check ATM available cash + update
                            if (theATM.checkCashAvailabilityAndUpdateAmt(Double.parseDouble(givenAmtToWithdraw), Transaction.WITHDRAW)) {
                                //2. update account balance
                                account.createTransaction(Double.parseDouble(givenAmtToWithdraw), Transaction.WITHDRAW);
                            } else {
                                System.out.println("The ATM cash is not enough for this type of transaction.");
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Your balance is not enough for this type of transaction. Please try to deposit some money first.");
                        exit = true;
                    }
                } else if (givenAction.trim().equals("2")){
                    System.out.println("Give the amount of money you want to deposit: ");
                    String givenAmtToDeposit = scanner.next();
                    if(givenAmtToDeposit.equals("quit")) {
                        exit = true;
                    }
                    while (!isDouble(givenAmtToDeposit) || !theATM.isAmtValid(Double.parseDouble(givenAmtToDeposit))) {
                        System.out.println("Please give a valid amount...");
                        givenAmtToDeposit = scanner.next();
                        if(givenAmtToDeposit.equals("quit")) {
                            exit = true;
                            break;
                        }
                    }
                    try {
                        if (!exit) {
                            //1. update ATM cash
                            if (theATM.checkCashAvailabilityAndUpdateAmt(Double.parseDouble(givenAmtToDeposit), Transaction.DEPOSIT)) {
                                //2. update account balance
                                account.createTransaction(Double.parseDouble(givenAmtToDeposit), Transaction.DEPOSIT);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Something went wrong with the deposit. Please try again");
                        exit = true;
                    }
                } else {
                    if (!givenAction.equals("quit")) {
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

    public static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }
}
