package jk.projects.model;


import java.sql.Timestamp;

/**
 * Created by JK on 8/5/2018.
 * This is for personal use. All Rights Reserved.
 */
public class Withdraw extends Transaction {

    public Withdraw(double amt, String time, Account act) {
        super(amt, time, act);
    }

    public void updateAcctBalance() throws Exception{
        if (this.amount <= this.account.getBalance()) {
            this.account.setBalance(this.account.getBalance() - this.amount);
            System.out.println("-----------------Account--------------");
            System.out.println("Total Balance: " + this.account.getBalance());
            System.out.println("--------------------------------------");
        } else {
            throw new Exception("Account balance does not permit this transaction");
        }
    }
}
