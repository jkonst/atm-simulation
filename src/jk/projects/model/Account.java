package jk.projects.model;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by JK on 8/5/2018.
 * This is for personal use. All Rights Reserved.
 */
public class Account {
    private double balance;
    private String id;
    private List<Transaction> myTransactions;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");

    public Account(double balance) {
        this.balance = balance;
        myTransactions = new ArrayList<>();
        System.out.println("-------------Account------------");
        System.out.println("Total Balance: " + this.balance);
        System.out.println("--------------------------------");
    }

    public void createTransaction(double amount, int type) throws Exception {
        Transaction transaction;
        if (type == 1) { //withdrawal
            transaction = new Withdraw(amount, sdf.format(new Timestamp(System.currentTimeMillis())), this);
        } else { //deposit
            transaction = new Deposit(amount, sdf.format(new Timestamp(System.currentTimeMillis())), this);
        }
        transaction.updateAcctBalance();
        myTransactions.add(transaction);
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void reportHistory() {
        //TODO
    }

    public double getBalance() {
        return balance;
    }

    public String getId() {
        return id;
    }
}
