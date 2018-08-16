package jk.projects.model;

import java.sql.Timestamp;

/**
 * Created by JK on 8/5/2018.
 * This is for personal use. All Rights Reserved.
 */
public class Deposit extends Transaction {

    public Deposit(double amt, String time, Account act) {
        super(amt, time, act);
    }

    public void updateAcctBalance() {
        this.account.setBalance(this.account.getBalance() + this.amount);
        System.out.println("-----------------Account--------------");
        System.out.println("Total Balance: " + this.account.getBalance());
        System.out.println("--------------------------------------");
    }
}
