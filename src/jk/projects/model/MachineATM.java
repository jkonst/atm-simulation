package jk.projects.model;

/**
 * Created by JK on 8/5/2018.
 * This is for personal use. All Rights Reserved.
 */
public class MachineATM {
    private double amount;
    private int fifties;
    private int twenties;
    private static MachineATM instance = new MachineATM();

    private MachineATM(){}

    public void init(double amount) {
        this.amount = amount;
        calculateNotes(amount);
    }

    private void calculateNotes(double amount) {

        int givenHundreds = (int) amount / 100;
        int rest = (int) amount % 100;
        int givenTwenties = 0;
        int givenFifties = 0;
        for (int i=1; i <= givenHundreds; i++) {
            if (i % 2 == 0) {
                givenTwenties += 5;
            } else {
                givenFifties += 2;
            }
        }
        if (rest % 50 == 0) {
            givenFifties += rest / 50;
        } else if (rest % 20 == 0) {
            givenTwenties += rest / 20;
        }
        this.twenties = givenTwenties;
        this.fifties = givenFifties;

        this.amount = this.twenties * 20 + this.fifties * 50;
        System.out.println("--------------ATM Cash-------------");
        System.out.println("Total Amount: " + this.amount);
        System.out.println("Notes of 50s: " + this.fifties);
        System.out.println("Notes of 20s: " + this.twenties);
        System.out.println("-----------------------------------");
    }

    public double getAmount() {
        return amount;
    }

    public static MachineATM getInstance() {
        return instance;
    }

    public boolean isAmtValid(double amount) {
        if (amount % 50 == 0 && amount % 20 == 0) {

            return true;
        } else if (amount % 50 == 0 && amount % 20 != 0) {

            return true;
        } else if (amount % 50 != 0 && amount % 20 == 0) {

            return true;
        } else { //
            return false;
        }
    }

    /**
     * This method is called only if isAmtValid(amount) has returned true
     * @param amount
     * @param type
     * @return true if atm cash is enough and type = 1 or type = 2
     */
    public boolean checkCashAvailabilityAndUpdateAmt(double amount, int type) {
        if (amount <= this.amount || type == Transaction.DEPOSIT) {
            if (amount % 50 == 0 && amount % 20 == 0) { //both i.e. find 100s and give some as 50s and some other as 20s
                int givenHundreds = (int) amount / 100;
                int givenFifties = 0;
                int givenTwenties = 0;
                for (int i = 1; i <= givenHundreds; i++) {
                    if (i % 2 == 0) {
                        givenTwenties += 5;
                    } else {
                        givenFifties += 2;
                    }
                }
                if ((this.twenties > givenTwenties && this.fifties > givenFifties) || type == Transaction.DEPOSIT) {
                    changeAmt(givenTwenties, givenFifties, type);
                    return true;
                } else {
                    return false;
                }

            } else if (amount % 50 == 0 && amount % 20 != 0) { //50s only
                int givenFifties = (int) amount / 50;
                if (this.fifties > givenFifties || type == Transaction.DEPOSIT) {
                    changeAmt(0, givenFifties, type);
                    return true;
                } else {
                    return false;
                }
            } else { //20s only
                int givenTwenties = (int) amount / 20;
                if (this.twenties > givenTwenties || type == Transaction.WITHDRAW) {
                    changeAmt(givenTwenties, 0, type);
                    return true;
                } else {
                    return false;
                }
            }
        } else {
            return false;
        }
    }

    public void changeAmt(int twenties, int fifties, int type) {
        if (type == Transaction.DEPOSIT) {
            this.twenties += twenties;
            this.fifties += fifties;
        } else if (type == Transaction.WITHDRAW) {
            this.twenties -= twenties;
            this.fifties -= fifties;
        }
        this.amount = this.twenties * 20 + this.fifties * 50;
        System.out.println("-------------ATM Cash-------------");
        System.out.println("Total Amount: " + this.amount);
        System.out.println("----------------------------------");
    }

    public int getFifties() {
        return fifties;
    }

    public int getTwenties() {
        return twenties;
    }
}
